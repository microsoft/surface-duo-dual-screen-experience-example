/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.microsoft.device.display.sampleheroapp.BuildConfig
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.config.MapConfig.TEST_MODE_ENABLED
import com.microsoft.device.display.sampleheroapp.config.MapConfig.ZOOM_LEVEL_CITY
import com.microsoft.device.display.sampleheroapp.config.MapConfig.ZOOM_LEVEL_STORES
import com.microsoft.device.display.sampleheroapp.domain.store.model.MapMarkerModel
import com.microsoft.device.display.sampleheroapp.domain.store.model.MarkerType
import com.microsoft.device.display.sampleheroapp.domain.store.model.Store
import com.microsoft.device.display.sampleheroapp.presentation.store.StoreViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.showToolbar
import com.microsoft.maps.MapAnimationKind
import com.microsoft.maps.MapElementCollisionBehavior
import com.microsoft.maps.MapElementLayer
import com.microsoft.maps.MapIcon
import com.microsoft.maps.MapImage
import com.microsoft.maps.MapProjection
import com.microsoft.maps.MapRenderMode
import com.microsoft.maps.MapScene
import com.microsoft.maps.MapStyleSheets
import com.microsoft.maps.MapView

class StoreMapFragment : Fragment() {

    private val viewModel: StoreViewModel by activityViewModels()

    private lateinit var mapView: MapView
    private val mapLayer: MapElementLayer = MapElementLayer()

    private var selectableMarkerMap: HashMap<String, MapIconSelectable> = HashMap()
    private var markerFactory: MapMarkerFactory? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_store_map, container, false)
        val mapContainer = layout.findViewById<FrameLayout>(R.id.mapContainer)

        setupMapView(savedInstanceState)
        mapContainer?.addView(mapView)

        markerFactory = MapMarkerFactory(requireContext())

        return layout
    }

    private fun setupMapView(savedInstanceState: Bundle?) {
        mapView = MapView(requireContext(), MapRenderMode.RASTER)
        mapView.onCreate(savedInstanceState)
        mapView.setCredentialsKey(BuildConfig.BING_MAPS_KEY)

        mapView.layers.add(mapLayer)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMap()
        setupRecenterButton()
        setupObservers()
    }

    private fun setupMap() {
        mapView.mapProjection = MapProjection.WEB_MERCATOR
        mapView.mapStyleSheet = MapStyleSheets.roadDark()
        mapView.userInterfaceOptions.isZoomButtonsVisible = false
        mapView.userInterfaceOptions.isZoomGestureEnabled = false
        mapView.userInterfaceOptions.isCompassButtonVisible = false
        mapView.userInterfaceOptions.isTiltButtonVisible = false
    }

    private fun setupRecenterButton() {
        activity?.findViewById<FloatingActionButton>(R.id.reset_fab)?.setOnClickListener {
            viewModel.markersCenter.value?.let { center ->
                returnMapToCenter(center)
            }
        }
    }

    private fun setupObservers() {
        viewModel.selectedCity.observe(
            viewLifecycleOwner,
            {
                unSelectAllMarkers()
                changeActionBarTitle(it, viewModel.selectedStore.value)
            }
        )
        viewModel.markersList.observe(
            viewLifecycleOwner,
            { data -> data?.takeIf { it.isNotEmpty() }?.let { addMarkersToMap(it) } }
        )
        viewModel.markersCenter.observe(
            viewLifecycleOwner,
            { center -> center?.let { resetMap(center, selectZoomLevel()) } }
        )
        viewModel.selectedStore.observe(
            viewLifecycleOwner,
            {
                changeActionBarTitle(viewModel.selectedCity.value, it)
                unSelectAllMarkers()
                selectMarker(it)
            }
        )
    }

    private fun selectZoomLevel(): Double =
        if (shouldZoomIn()) ZOOM_LEVEL_STORES else ZOOM_LEVEL_CITY

    private fun shouldZoomIn(): Boolean = viewModel.selectedCity.value != null

    private fun unSelectAllMarkers() {
        selectableMarkerMap.keys
            .filter { selectableMarkerMap[it]?.isSelected ?: false }
            .forEach { key ->
                markerFactory?.let { factory ->
                    selectableMarkerMap[key]?.image = MapImage(factory.createBitmapWithText(key))
                    selectableMarkerMap[key]?.isSelected = false
                }
            }
    }

    private fun selectMarker(store: Store?) {
        store?.let {
            selectableMarkerMap[it.name]?.image =
                markerFactory?.let { factory ->
                    MapImage(factory.createBitmapWithText(it.name, true))
                }
            selectableMarkerMap[it.name]?.isSelected = true
        }
    }

    private fun changeActionBarTitle(city: MapMarkerModel?, store: Store?) {
        if (city == null && store == null) {
            activity?.showToolbar(false)
            activity?.changeToolbarTitle(getString(R.string.app_name))
        }
    }

    private fun resetMap(center: MapMarkerModel, zoomLevel: Double) {
        mapView.setScene(
            MapScene.createFromLocationAndZoomLevel(center.toGeopoint(), zoomLevel),
            getMapAnimations()
        )
    }

    private fun returnMapToCenter(center: MapMarkerModel) {
        mapView.panTo(center.toGeopoint())
    }

    private fun getMapAnimations() =
        if (TEST_MODE_ENABLED) {
            MapAnimationKind.NONE
        } else {
            MapAnimationKind.DEFAULT
        }

    private fun addMarkersToMap(markers: List<MapMarkerModel>) {
        mapLayer.elements.clear()
        markers.forEach { markerModel ->
            val marker: MapIcon? = when (markerModel.type) {
                MarkerType.PIN -> buildBalloonMarker(markerModel).also {
                    selectableMarkerMap[markerModel.name] = it
                }
                MarkerType.CIRCLE -> buildCircleMarker(markerModel)
                else -> null
            }
            marker?.let { icon ->
                mapLayer.elements.add(icon)
            }
        }

        mapView.addOnMapCameraChangedListener {
            markers
                .filter { mapView.bounds.isInBounds(it) && it.type == MarkerType.PIN }
                .map { it.id }
                .let { viewModel.updateStoreList(it) }

            true
        }

        mapView.addOnMapTappedListener { args ->
            val mapIcon =
                mapView.findMapElementsAtOffset(args.position).takeIf { it.isNotEmpty() }?.first()
            markers
                .firstOrNull { it.name == mapIcon?.tag }
                ?.let {
                    when (it.type) {
                        MarkerType.PIN -> {
                            viewModel.navigateToDetails(it)
                            true
                        }
                        MarkerType.CIRCLE -> {
                            viewModel.navigateToList(it)
                            true
                        }
                        else -> false
                    }
                }
            true
        }
    }

    private fun buildBalloonMarker(marker: MapMarkerModel) =
        MapIconSelectable().apply {
            location = marker.toGeopoint()
            tag = marker.name
            contentDescription = marker.name
            image = markerFactory?.let { factory ->
                MapImage(factory.createBitmapWithText(marker.name))
            }
            desiredCollisionBehavior = MapElementCollisionBehavior.HIDE
        }

    private fun buildCircleMarker(marker: MapMarkerModel) =
        MapIcon().apply {
            location = marker.toGeopoint()
            tag = marker.name
            contentDescription = marker.name
            image =
                ResourcesCompat
                    .getDrawable(resources, R.drawable.ic_circle_marker, null)
                    ?.let { MapImage(it.toBitmap()) }
        }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        markerFactory = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
