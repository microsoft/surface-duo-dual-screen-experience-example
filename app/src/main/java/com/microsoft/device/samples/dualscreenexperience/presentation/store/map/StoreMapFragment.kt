/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.TEST_MODE_ENABLED
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.ZOOM_LEVEL_CITY
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.ZOOM_LEVEL_STORES
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentStoreMapBinding
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MarkerType
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.Store
import com.microsoft.device.samples.dualscreenexperience.presentation.store.StoreViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.NetworkConnectionLiveData
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class StoreMapFragment : Fragment() {

    @Inject lateinit var mapController: MapController

    private val viewModel: StoreViewModel by activityViewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()

    private lateinit var mapView: FrameLayout

    private var markerFactory: MapMarkerFactory? = null
    private var binding: FragmentStoreMapBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreMapBinding.inflate(inflater, container, false)
        binding?.isConnected = true
        onWindowLayoutInfoChanged()

        setupMapView(savedInstanceState)

        markerFactory = MapMarkerFactory(requireContext())

        return binding?.root
    }

    private fun setupMapView(savedInstanceState: Bundle?) {
        mapView = mapController.generateMapView(requireContext())
        binding?.mapContainer?.addView(mapView)

        mapController.onCreate(mapView, savedInstanceState)

        lifecycle.coroutineScope.launchWhenCreated {
            mapController.generateController(mapView)

            mapController.setupMap(requireContext(), mapView)
            setupObservers()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMapAvailabilityMessage()
        setupRecenterButton()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeWindowLayoutInfo(context as AppCompatActivity)
    }

    private fun observeWindowLayoutInfo(activity: AppCompatActivity) {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                WindowInfoTracker.getOrCreate(activity)
                    .windowLayoutInfo(activity)
                    .collect {
                        onWindowLayoutInfoChanged()
                    }
            }
        }
    }

    private fun onWindowLayoutInfoChanged() {
        binding?.isDualPortrait =
            layoutInfoViewModel.isDualPortraitMode() && viewModel.isNavigationAtStart()
    }

    private fun setupMapAvailabilityMessage() {
        activity?.applicationContext?.let {
            if (mapController.shouldShowNoGmsMessage(it)) {
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
                NetworkConnectionLiveData(it).observe(viewLifecycleOwner) { isConnected ->
                    onWindowLayoutInfoChanged()
                    binding?.isConnected = isConnected
                }
            }
        }
    }

    private fun setupRecenterButton() {
        binding?.resetFab?.setOnClickListener {
            viewModel.markersCenter.value?.let { center ->
                mapController.returnMapToCenter(mapView, center)
            }
        }
    }

    private fun setupObservers() {
        viewModel.selectedCity.observe(viewLifecycleOwner) {
            onWindowLayoutInfoChanged()
            viewModel.markersList.value?.let { newData ->
                addMarkersToMap(newData)
            }
            changeActionBarTitle(it, viewModel.selectedStore.value)
        }
        viewModel.markersCenter.observe(viewLifecycleOwner) { center ->
            center?.let { resetMap(center, selectZoomLevel()) }
        }
        viewModel.selectedStore.observe(viewLifecycleOwner) {
            onWindowLayoutInfoChanged()
            changeActionBarTitle(viewModel.selectedCity.value, it)
            mapController.unSelectAllMarkers(markerFactory)
            it?.let { store ->
                mapController.selectMarker(
                    store.name,
                    markerFactory?.createBitmapWithText(store.name, true)
                )
            }
        }
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
            mapController.resetMapWithAnimations(mapView, center, zoomLevel)
        } else {
            mapController.resetMapWithoutAnimations(mapView, center, zoomLevel)
        }
    }

    private fun shouldEnableAnimations() =
        !TEST_MODE_ENABLED &&
            layoutInfoViewModel.isDualMode.value == true &&
            !mapController.isZoomEqualTo(mapView, selectZoomLevel())

    private fun addMarkersToMap(markers: List<MapMarkerModel>) {
        mapController.clearMap()
        markers.forEach { markerModel ->
            when (markerModel.type) {
                MarkerType.PIN -> addBalloonMarker(markerModel)
                MarkerType.CIRCLE -> addCircleMarker(markerModel)
                else -> {}
            }
        }

        mapController.setOnCameraMoveListener(mapView) {
            markers
                .filter { mapController.isMarkerVisible(mapView, it) && it.type == MarkerType.PIN }
                .map { it.id }
                .let { viewModel.updateStoreList(it) }
        }

        mapController.setOnMapClickListener(
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
                        if (!mapController.isMarkerCenter(mapView, it)) {
                            mapController.resetMapWithoutAnimations(mapView, it, selectZoomLevel())
                        }
                    }

                    mapController.getSelectedMarkerName()?.let { selectedMarkerName ->
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
        mapController.addMarker(
            model = markerModel,
            icon = markerFactory?.createBitmapWithText(markerModel.name),
            visible = !(markerModel.hasCity == true && viewModel.selectedCity.value == null),
            shouldHideIfCollision = true,
            isSelectable = true
        )

    private fun addCircleMarker(markerModel: MapMarkerModel) =
        mapController.addMarker(
            model = markerModel,
            icon = markerFactory?.createCircleBitmapWithText(
                getString(R.string.store_map_circle_touch_tutorial)
            ),
            visible = true,
            shouldHideIfCollision = false
        )

    override fun onStart() {
        super.onStart()
        mapController.onStart(mapView)
    }

    override fun onResume() {
        super.onResume()
        mapController.onResume(mapView)
    }

    override fun onPause() {
        super.onPause()
        mapController.onPause(mapView)
    }

    override fun onStop() {
        super.onStop()
        mapController.onStop(mapView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        markerFactory = null
        mapController.onDestroy(mapView)
        binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapController.onLowMemory(mapView)
    }
}
