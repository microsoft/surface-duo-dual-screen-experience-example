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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoreMapFragment : Fragment() {

    private val viewModel: StoreViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    private var googleMap: GoogleMap? = null

    private var selectableMarkerMap: HashMap<String, MarkerSelectable> = HashMap()
    private var markerFactory: MapMarkerFactory? = null
    private var binding: FragmentStoreMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreMapBinding.inflate(inflater, container, false)
        binding?.rotationViewModel = rotationViewModel
        binding?.isScreenDual = viewModel.isNavigationAtStart()

        binding?.map?.onCreate(savedInstanceState)

        lifecycle.coroutineScope.launchWhenCreated {
            googleMap = binding?.map?.awaitMap()

            setupMap()
            setupObservers()
        }

        markerFactory = MapMarkerFactory(requireContext())

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMapAvailabilityMessage()
        setupRecenterButton()
    }

    private fun setupMapAvailabilityMessage() {
        activity?.applicationContext?.let {
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(it) != ConnectionResult.SUCCESS) {
                binding?.noInternetConnectionSingleMode?.apply {
                    noInternetConnectionTitle.setText(R.string.no_google_services_title)
                    noInternetConnectionDescription.setText(R.string.no_google_services_description)
                    noInternetConnectionImageSingle.setImageResource(R.drawable.ic_no_google_services)
                }

                binding?.noInternetConnectionDualMode?.apply {
                    noInternetConnectionTitle.setText(R.string.no_google_services_title)
                    noInternetConnectionDescription.setText(R.string.no_google_services_description)
                    noInternetConnectionImageDual.setImageResource(R.drawable.ic_no_google_services)
                }

                binding?.isConnected = false
            } else {
                NetworkConnectionLiveData(context).observe(
                    viewLifecycleOwner,
                    { isConnected ->
                        binding?.isScreenDual = viewModel.isNavigationAtStart()
                        binding?.isConnected = isConnected
                    }
                )
            }
        }
    }

    private fun setupMap() {
        context?.let {
            googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(it, R.raw.map_style))
        }
        googleMap?.uiSettings?.apply {
            isScrollGesturesEnabled = true
            isCompassEnabled = false
            isZoomGesturesEnabled = false
            isZoomControlsEnabled = false
            isMapToolbarEnabled = false
            isTiltGesturesEnabled = false
            isScrollGesturesEnabledDuringRotateOrZoom = false
            isRotateGesturesEnabled = false
            isIndoorLevelPickerEnabled = false
            isMyLocationButtonEnabled = false
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
                binding?.isScreenDual = viewModel.isNavigationAtStart()
                viewModel.markersList.value?.let { newData ->
                    addMarkersToMap(newData)
                }
                changeActionBarTitle(it, viewModel.selectedStore.value)
            }
        )
        viewModel.markersCenter.observe(
            viewLifecycleOwner,
            { center -> center?.let { resetMap(center, selectZoomLevel()) } }
        )
        viewModel.selectedStore.observe(
            viewLifecycleOwner,
            {
                binding?.isScreenDual = viewModel.isNavigationAtStart()
                changeActionBarTitle(viewModel.selectedCity.value, it)
                unSelectAllMarkers()
                selectMarker(it)
            }
        )
    }

    private fun selectZoomLevel(): Float =
        if (shouldZoomIn()) ZOOM_LEVEL_STORES else ZOOM_LEVEL_CITY

    private fun shouldZoomIn(): Boolean = viewModel.selectedCity.value != null

    private fun unSelectAllMarkers() {
        selectableMarkerMap.keys
            .filter { selectableMarkerMap[it]?.isSelected ?: false }
            .forEach { key ->
                markerFactory?.let { factory ->
                    selectableMarkerMap[key]?.googleModel?.setIcon(
                        BitmapDescriptorFactory.fromBitmap(factory.createBitmapWithText(key))
                    )
                    selectableMarkerMap[key]?.isSelected = false
                }
            }
    }

    private fun selectMarker(store: Store?) {
        store?.let {
            selectableMarkerMap[it.name]?.googleModel?.setIcon(
                markerFactory?.let { factory ->
                    BitmapDescriptorFactory.fromBitmap(factory.createBitmapWithText(it.name, true))
                }
            )
            selectableMarkerMap[it.name]?.isSelected = true
        }
    }

    private fun changeActionBarTitle(city: MapMarkerModel?, store: Store?) {
        if (viewModel.isNavigationAtStart() && city == null && store == null) {
            appCompatActivity?.setupToolbar(isBackButtonEnabled = false)
            appCompatActivity?.changeToolbarTitle(getString(R.string.app_name))
        }
    }

    private fun resetMap(center: MapMarkerModel, zoomLevel: Float) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(center.toLatLng(), zoomLevel)
        if (shouldEnableAnimations()) {
            googleMap?.animateCamera(cameraUpdate)
        } else {
            googleMap?.moveCamera(cameraUpdate)
        }
    }

    private fun resetMapWithoutAnimations() {
        viewModel.markersCenter.value?.toLatLng()?.let {
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(it, selectZoomLevel()))
        }
    }

    private fun returnMapToCenter(center: MapMarkerModel) {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(center.toLatLng()))
    }

    private fun shouldEnableAnimations() =
        !TEST_MODE_ENABLED &&
            rotationViewModel.isDualMode.value == true &&
            googleMap?.cameraPosition?.zoom != selectZoomLevel()

    private fun addMarkersToMap(markers: List<MapMarkerModel>) {
        googleMap?.clear()
        markers.forEach { markerModel ->
            when (markerModel.type) {
                MarkerType.PIN -> addBalloonMarker(markerModel)?.let {
                    selectableMarkerMap[markerModel.name] = MarkerSelectable(it, false)
                }
                MarkerType.CIRCLE -> addCircleMarker(markerModel)
                else -> {}
            }
        }

        googleMap?.setOnCameraMoveListener {
            markers
                .filter { googleMap?.projection?.isInBounds(it) == true && it.type == MarkerType.PIN }
                .map { it.id }
                .let { viewModel.updateStoreList(it) }
        }

        googleMap?.setOnMarkerClickListener { clickedMarker ->
            markers.firstOrNull { it.name == clickedMarker.title }?.let { possibleMarker ->
                when (possibleMarker.type) {
                    MarkerType.PIN -> {
                        val isAlreadySelected = selectableMarkerMap.values
                            .firstOrNull { clickedMarker.title == it.googleModel.title }?.isSelected ?: false
                        if (isAlreadySelected) {
                            viewModel.navigateUp()
                        } else {
                            viewModel.navigateToDetails(possibleMarker)
                        }
                    }
                    MarkerType.CIRCLE -> {
                        viewModel.navigateToList(possibleMarker)
                    }
                    else -> {
                        // do nothing
                    }
                }
            }
            true
        }

        googleMap?.setOnMapClickListener {
            if (googleMap?.cameraPosition?.target != viewModel.markersCenter.value?.toLatLng()) {
                resetMapWithoutAnimations()
            }

            val selectedMarkerName = selectableMarkerMap.keys
                .firstOrNull { selectableMarkerMap[it]?.isSelected ?: false }
            val isStore = markers.firstOrNull { it.name == selectedMarkerName } != null
            if (isStore) {
                viewModel.navigateUp()
            }
        }
    }

    private fun addBalloonMarker(markerModel: MapMarkerModel): Marker? =
        googleMap?.addMarker {
            position(markerModel.toLatLng())
            title(markerModel.name)
            visible(!(markerModel.hasCity == true && viewModel.selectedCity.value == null))
            icon(
                markerFactory?.let { factory ->
                    BitmapDescriptorFactory.fromBitmap(factory.createBitmapWithText(markerModel.name))
                }
            )
        }

    private fun addCircleMarker(markerModel: MapMarkerModel): Marker? =
        googleMap?.addMarker {
            position(markerModel.toLatLng())
            title(markerModel.name)
            icon(
                markerFactory?.let { factory ->
                    BitmapDescriptorFactory.fromBitmap(
                        factory.createCircleBitmapWithText(
                            getString(R.string.store_map_circle_touch_tutorial)
                        )
                    )
                }
            )
        }

    override fun onStart() {
        super.onStart()
        binding?.map?.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding?.map?.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding?.map?.onSaveInstanceState(outState)
    }

    override fun onPause() {
        super.onPause()
        binding?.map?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding?.map?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        markerFactory = null
        googleMap = null
        selectableMarkerMap.clear()
        binding?.map?.onDestroy()
        binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding?.map?.onLowMemory()
    }
}
