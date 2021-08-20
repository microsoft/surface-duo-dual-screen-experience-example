/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.microsoft.device.samples.dualscreenexperience.ITokenProvider
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.TEST_MODE_ENABLED
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.ZOOM_LEVEL_CITY
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.ZOOM_LEVEL_STORES
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentStoreMapBinding
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MarkerType
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.Store
import com.microsoft.device.samples.dualscreenexperience.presentation.store.StoreViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.NetworkConnectionLiveData
import com.microsoft.device.samples.dualscreenexperience.presentation.util.RotationViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import com.microsoft.maps.MapAnimationKind
import com.microsoft.maps.MapElementCollisionBehavior
import com.microsoft.maps.MapElementLayer
import com.microsoft.maps.MapFlyout
import com.microsoft.maps.MapIcon
import com.microsoft.maps.MapImage
import com.microsoft.maps.MapLoadingStatus
import com.microsoft.maps.MapProjection
import com.microsoft.maps.MapRenderMode
import com.microsoft.maps.MapScene
import com.microsoft.maps.MapStyleSheets
import com.microsoft.maps.MapView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StoreMapFragment : Fragment() {

    @Inject lateinit var tokenProvider: ITokenProvider

    private val viewModel: StoreViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    private lateinit var mapView: MapView
    private var mapLayer: MapElementLayer? = null

    private var selectableMarkerMap: HashMap<String, MapIconSelectable> = HashMap()
    private var markerFactory: MapMarkerFactory? = null
    private var binding: FragmentStoreMapBinding? = null

    private var hasClickedMarker = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreMapBinding.inflate(inflater, container, false)
        binding?.rotationViewModel = rotationViewModel

        setupMapView(savedInstanceState)
        binding?.mapContainer?.addView(mapView)

        markerFactory = MapMarkerFactory(requireContext())

        return binding?.root
    }

    private fun setupMapView(savedInstanceState: Bundle?) {
        mapLayer = MapElementLayer()
        mapView = MapView(requireActivity(), MapRenderMode.RASTER)
        mapView.onCreate(savedInstanceState)
        setupMapKey()

        mapLayer?.let {
            mapView.layers.add(it)
        }
    }

    private fun setupMapKey() {
        lifecycleScope.launch {
            mapView.setCredentialsKey(tokenProvider.getMapToken())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNetworkObserver()
        setupMap()
        setupRecenterButton()
        setupObservers()
    }

    private fun setupNetworkObserver() {
        NetworkConnectionLiveData(context).observe(
            viewLifecycleOwner,
            { isConnected ->
                binding?.isConnected = isConnected
            }
        )
    }

    private fun setupMap() {
        mapView.mapProjection = MapProjection.WEB_MERCATOR
        mapView.mapStyleSheet = MapStyleSheets.roadDark()
        mapView.userInterfaceOptions.apply {
            isZoomButtonsVisible = false
            isZoomGestureEnabled = false
            isCompassButtonVisible = false
            isTiltButtonVisible = false
            isTiltGestureEnabled = false
            isRotateGestureEnabled = false
            isUserLocationButtonVisible = false
            isDirectionsButtonVisible = false
            isSaveMapUserPreferencesEnabled = false
            isStylePickerButtonVisible = false
        }
    }

    private fun setupRecenterButton() {
        binding?.resetFab?.setOnClickListener {
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
            appCompatActivity?.setupToolbar(isBackButtonEnabled = false)
            appCompatActivity?.changeToolbarTitle(getString(R.string.app_name))
        }
    }

    private fun resetMap(center: MapMarkerModel, zoomLevel: Double) {
        if (!hasClickedMarker) {
            hasClickedMarker =
                viewModel.selectedCity.value != null || viewModel.selectedStore.value != null
        }
        mapView.setScene(
            MapScene.createFromLocationAndZoomLevel(center.toGeopoint(), zoomLevel),
            getMapAnimations()
        )
    }

    private fun resetMapWithoutAnimations() {
        viewModel.markersCenter.value?.toGeopoint()?.let {
            mapView.setScene(
                MapScene.createFromLocationAndZoomLevel(it, selectZoomLevel()),
                MapAnimationKind.NONE
            )
        }
    }

    private fun returnMapToCenter(center: MapMarkerModel) {
        mapView.panTo(center.toGeopoint())
    }

    private fun getMapAnimations() =
        if (shouldEnableAnimations()) {
            MapAnimationKind.DEFAULT
        } else {
            MapAnimationKind.NONE
        }

    private fun shouldEnableAnimations() =
        !TEST_MODE_ENABLED && rotationViewModel.isDualMode.value == true && hasClickedMarker

    private fun addMarkersToMap(markers: List<MapMarkerModel>) {
        mapLayer?.elements?.clear()
        markers.forEach { markerModel ->
            val marker: MapIcon? = when (markerModel.type) {
                MarkerType.PIN -> buildBalloonMarker(markerModel).also {
                    selectableMarkerMap[markerModel.name] = it
                }
                MarkerType.CIRCLE -> buildCircleMarker(markerModel)
                else -> null
            }
            marker?.let { icon ->
                mapLayer?.elements?.add(icon)
            }
        }

        mapView.addOnMapCameraChangedListener {
            markers
                .filter { mapView.bounds.isInBounds(it) && it.type == MarkerType.PIN }
                .map { it.id }
                .let { viewModel.updateStoreList(it) }

            true
        }

        mapView.addOnMapLoadingStatusChangedListener {
            if (!isMapLoading()) {
                mapLayer?.elements?.forEach { element ->
                    (element as? MapIcon?)?.flyout?.let { iconFlyout ->
                        showFlyout(iconFlyout)
                    }
                }
            }
            true
        }

        mapView.addOnMapTappedListener { args ->
            if (!isMapLoading()) {
                val mapIcon =
                    mapView.findMapElementsAtOffset(args.position).takeIf { it.isNotEmpty() }
                        ?.first()
                val possibleMarker = markers.firstOrNull { it.name == mapIcon?.tag }
                if (possibleMarker != null) {
                    when (possibleMarker.type) {
                        MarkerType.PIN -> {
                            val isAlreadySelected = selectableMarkerMap.values
                                .firstOrNull { mapIcon?.tag == it.tag }?.isSelected ?: false
                            if (isAlreadySelected) {
                                viewModel.navigateUp()
                            } else {
                                viewModel.navigateToDetails(possibleMarker)
                            }
                        }
                        MarkerType.CIRCLE -> {
                            if (viewModel.shouldShowTouchCity()) {
                                viewModel.disableShowTouchCity()
                                viewModel.markersList.value?.let {
                                    addMarkersToMap(it)
                                }
                            }
                            viewModel.navigateToList(possibleMarker)
                        }
                        else -> {
                            // do nothing
                        }
                    }
                } else {
                    val selectedMarkerName = selectableMarkerMap.keys
                        .firstOrNull { selectableMarkerMap[it]?.isSelected ?: false }
                    val isStore = markers.firstOrNull { it.name == selectedMarkerName } != null
                    if (isStore) {
                        viewModel.navigateUp()
                    }
                }
                true
            } else {
                resetMapWithoutAnimations()
                false
            }
        }
    }

    private fun isMapLoading() =
        mapView.loadingStatus == MapLoadingStatus.UPDATING ||
            mapView.loadingStatus == MapLoadingStatus.UPDATING_WITH_BASICS_COMPLETE

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
            val imageDrawable = if (viewModel.shouldShowTouchCity()) {
                R.drawable.ic_circle_marker_touch
            } else {
                R.drawable.ic_circle_marker
            }
            image =
                ResourcesCompat
                    .getDrawable(resources, imageDrawable, null)
                    ?.let { MapImage(it.toBitmap()) }
            if (viewModel.shouldShowTouchCity()) {
                flyout = MapFlyout().apply {
                    isLightDismissEnabled = false
                }
            }
        }

    private fun showFlyout(flyout: MapFlyout) {
        flyout.apply {
            title = getString(R.string.store_map_circle_touch_tutorial)
            styleDefaultView(
                ResourcesCompat.getColor(resources, R.color.medium_gray, null),
                ResourcesCompat.getColor(resources, R.color.white, null)
            )
            show()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        mapLayer = null
        mapView.onDestroy()
        markerFactory = null
        binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
