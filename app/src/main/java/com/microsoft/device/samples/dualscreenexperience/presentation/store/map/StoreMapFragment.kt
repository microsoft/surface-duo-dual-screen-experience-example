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
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
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
import javax.inject.Inject

@AndroidEntryPoint
class StoreMapFragment : Fragment() {

    @Inject lateinit var mapComponent: IMapComponent

    private val viewModel: StoreViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    private lateinit var mapView: FrameLayout

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

        setupMapView(savedInstanceState)

        markerFactory = MapMarkerFactory(requireContext())

        return binding?.root
    }

    private fun setupMapView(savedInstanceState: Bundle?) {
        mapView = mapComponent.generateMapView(requireContext())
        binding?.mapContainer?.addView(mapView)

        mapComponent.onCreate(mapView, savedInstanceState)

        lifecycle.coroutineScope.launchWhenCreated {
            mapComponent.generateController(mapView)

            mapComponent.setupMap(requireContext(), mapView)
            setupObservers()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMapAvailabilityMessage()
        setupRecenterButton()
    }

    private fun setupMapAvailabilityMessage() {
        activity?.applicationContext?.let {
            if (mapComponent.shouldShowNoGmsMessage(it)) {
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

    private fun setupRecenterButton() {
        binding?.resetFab?.setOnClickListener {
            viewModel.markersCenter.value?.let { center ->
                mapComponent.returnMapToCenter(mapView, center)
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
                mapComponent.unSelectAllMarkers(markerFactory)
                it?.let { store ->
                    mapComponent.selectMarker(
                        store.name,
                        markerFactory?.createBitmapWithText(store.name, true)
                    )
                }
            }
        )
    }

    private fun selectZoomLevel(): Float =
        if (shouldZoomIn()) ZOOM_LEVEL_STORES else ZOOM_LEVEL_CITY

    private fun shouldZoomIn(): Boolean = viewModel.selectedCity.value != null

    private fun changeActionBarTitle(city: MapMarkerModel?, store: Store?) {
        if (viewModel.isNavigationAtStart() && city == null && store == null) {
            appCompatActivity?.setupToolbar(isBackButtonEnabled = false)
            appCompatActivity?.changeToolbarTitle(getString(R.string.app_name))
        }
    }

    private fun resetMap(center: MapMarkerModel, zoomLevel: Float) {
        if (shouldEnableAnimations()) {
            mapComponent.resetMapWithAnimations(mapView, center, zoomLevel)
        } else {
            mapComponent.resetMapWithoutAnimations(mapView, center, zoomLevel)
        }
    }

    private fun shouldEnableAnimations() =
        !TEST_MODE_ENABLED &&
            rotationViewModel.isDualMode.value == true &&
            !mapComponent.isZoomEqualTo(mapView, selectZoomLevel())

    private fun addMarkersToMap(markers: List<MapMarkerModel>) {
        mapComponent.clearMap()
        markers.forEach { markerModel ->
            when (markerModel.type) {
                MarkerType.PIN -> addBalloonMarker(markerModel)
                MarkerType.CIRCLE -> addCircleMarker(markerModel)
                else -> {}
            }
        }

        mapComponent.setOnCameraMoveListener(mapView) {
            markers
                .filter { mapComponent.isMarkerVisible(mapView, it) && it.type == MarkerType.PIN }
                .map { it.id }
                .let { viewModel.updateStoreList(it) }
        }

        mapComponent.setOnMapClickListener(
            mapView,
            object : OnMapClickListener {
                override fun onMarkerClicked(markerName: String?, isAlreadySelected: Boolean) {
                    markers.firstOrNull { it.name == markerName }?.let { possibleMarker ->
                        when (possibleMarker.type) {
                            MarkerType.PIN -> {
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
                }

                override fun onNoMarkerClicked() {
                    viewModel.markersCenter.value?.let {
                        if (!mapComponent.isMarkerCenter(mapView, it)) {
                            mapComponent.resetMapWithoutAnimations(mapView, it, selectZoomLevel())
                        }
                    }

                    mapComponent.getSelectedMarkerName()?.let { selectedMarkerName ->
                        markers
                            .firstOrNull { it.name == selectedMarkerName }
                            ?.takeIf { it.type == MarkerType.PIN }
                            ?.let { viewModel.navigateUp() }
                    }
                }
            }
        )
    }

    private fun addBalloonMarker(markerModel: MapMarkerModel) =
        mapComponent.addMarker(
            model = markerModel,
            icon = markerFactory?.createBitmapWithText(markerModel.name),
            visible = !(markerModel.hasCity == true && viewModel.selectedCity.value == null),
            shouldHideIfCollision = true,
            isSelectable = true
        )

    private fun addCircleMarker(markerModel: MapMarkerModel) =
        mapComponent.addMarker(
            model = markerModel,
            icon = markerFactory?.createCircleBitmapWithText(
                getString(R.string.store_map_circle_touch_tutorial)
            ),
            visible = true,
            shouldHideIfCollision = false
        )

    override fun onStart() {
        super.onStart()
        mapComponent.onStart(mapView)
    }

    override fun onResume() {
        super.onResume()
        mapComponent.onResume(mapView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapComponent.onSaveInstanceState(mapView, outState)
    }

    override fun onPause() {
        super.onPause()
        mapComponent.onPause(mapView)
    }

    override fun onStop() {
        super.onStop()
        mapComponent.onStop(mapView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        markerFactory = null
        mapComponent.onDestroy(mapView)
        binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapComponent.onLowMemory(mapView)
    }
}
