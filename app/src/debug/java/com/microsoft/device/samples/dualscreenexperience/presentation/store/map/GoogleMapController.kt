/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.LAT_INIT
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.LNG_INIT
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.ZOOM_LEVEL_CITY
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleMapController @Inject constructor() : MapController {

    private var googleMap: GoogleMap? = null
    private var selectableMarkerMap: HashMap<String, MarkerSelectable> = HashMap()

    override fun shouldShowNoGmsMessage(context: Context): Boolean =
        GoogleApiAvailability
            .getInstance()
            .isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS

    override fun generateMapView(context: Context): FrameLayout =
        MapView(
            context,
            GoogleMapOptions().camera(
                CameraPosition.fromLatLngZoom(LatLng(LAT_INIT, LNG_INIT), ZOOM_LEVEL_CITY)
            )
        )

    override suspend fun generateController(mapView: FrameLayout) {
        googleMap = (mapView as? MapView)?.awaitMap()
    }

    override fun onCreate(mapView: FrameLayout, savedInstanceState: Bundle?) {
        (mapView as? MapView)?.onCreate(savedInstanceState)
    }

    override fun onResume(mapView: FrameLayout) {
        (mapView as? MapView)?.onResume()
    }

    override fun onPause(mapView: FrameLayout) {
        (mapView as? MapView)?.onPause()
    }

    override fun onStart(mapView: FrameLayout) {
        (mapView as? MapView)?.onStart()
    }

    override fun onStop(mapView: FrameLayout) {
        (mapView as? MapView)?.onStop()
    }

    override fun onDestroy(mapView: FrameLayout) {
        googleMap = null
        selectableMarkerMap.clear()
        (mapView as? MapView)?.onDestroy()
    }

    override fun onSaveInstanceState(mapView: FrameLayout, outState: Bundle) {
        (mapView as? MapView)?.onSaveInstanceState(outState)
    }

    override fun onLowMemory(mapView: FrameLayout) {
        (mapView as? MapView)?.onLowMemory()
    }

    override fun setupMap(context: Context, mapView: FrameLayout) {
        googleMap?.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style))
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

    override fun resetMapWithAnimations(mapView: FrameLayout, center: MapMarkerModel, zoomLevel: Float) {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(center.toLatLng(), zoomLevel))
    }

    override fun resetMapWithoutAnimations(mapView: FrameLayout, center: MapMarkerModel, zoomLevel: Float) {
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(center.toLatLng(), zoomLevel))
    }

    override fun returnMapToCenter(mapView: FrameLayout, center: MapMarkerModel) {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(center.toLatLng()))
    }

    override fun clearMap() {
        googleMap?.clear()
    }

    override fun setOnCameraMoveListener(mapView: FrameLayout, callback: () -> Unit) {
        googleMap?.setOnCameraMoveListener(callback)
    }

    override fun setOnMapClickListener(mapView: FrameLayout, listener: OnMapClickListener) {
        googleMap?.setOnMarkerClickListener { clickedMarker ->
            val isAlreadySelected = selectableMarkerMap.values
                .firstOrNull { clickedMarker.title == it.googleModel.title }?.isSelected ?: false
            listener.onMarkerClicked(clickedMarker.title, isAlreadySelected)
            true
        }

        googleMap?.setOnMapClickListener {
            listener.onNoMarkerClicked()
        }
    }

    override fun isMarkerVisible(mapView: FrameLayout, model: MapMarkerModel): Boolean =
        googleMap?.projection?.isInBounds(model) == true

    override fun isMarkerCenter(mapView: FrameLayout, model: MapMarkerModel): Boolean =
        googleMap?.cameraPosition?.target == model.toLatLng()

    override fun addMarker(
        model: MapMarkerModel,
        icon: Bitmap?,
        visible: Boolean,
        shouldHideIfCollision: Boolean,
        isSelectable: Boolean
    ) {
        googleMap?.addMarker {
            position(model.toLatLng())
            title(model.name)
            visible(visible)
            icon(icon?.let { BitmapDescriptorFactory.fromBitmap(it) })
        }?.takeIf {
            isSelectable
        }?.let {
            selectableMarkerMap[model.name] = MarkerSelectable(it, false)
        }
    }

    override fun selectMarker(markerName: String?, newIcon: Bitmap?) {
        newIcon?.let {
            selectableMarkerMap[markerName]?.googleModel?.setIcon(
                BitmapDescriptorFactory.fromBitmap(it)
            )
            selectableMarkerMap[markerName]?.isSelected = true
        }
    }

    override fun unSelectAllMarkers(markerFactory: MapMarkerFactory?) {
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

    override fun getSelectedMarkerName(): String? =
        selectableMarkerMap
            .keys
            .firstOrNull { selectableMarkerMap[it]?.isSelected ?: false }

    override fun isZoomEqualTo(mapView: FrameLayout, zoomLevel: Float): Boolean =
        googleMap?.cameraPosition?.zoom == zoomLevel
}
