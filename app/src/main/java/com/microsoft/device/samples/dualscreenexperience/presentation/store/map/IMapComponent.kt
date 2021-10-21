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
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel

interface IMapComponent {

    fun shouldShowNoGmsMessage(context: Context): Boolean

    fun generateMapView(context: Context): FrameLayout
    suspend fun generateController(mapView: FrameLayout)

    fun onCreate(mapView: FrameLayout, savedInstanceState: Bundle?)
    fun onResume(mapView: FrameLayout)
    fun onPause(mapView: FrameLayout)
    fun onStart(mapView: FrameLayout)
    fun onStop(mapView: FrameLayout)
    fun onDestroy(mapView: FrameLayout)
    fun onSaveInstanceState(mapView: FrameLayout, outState: Bundle)
    fun onLowMemory(mapView: FrameLayout)

    fun setupMap(context: Context, mapView: FrameLayout)

    fun resetMapWithAnimations(mapView: FrameLayout, center: MapMarkerModel, zoomLevel: Float)
    fun resetMapWithoutAnimations(mapView: FrameLayout, center: MapMarkerModel, zoomLevel: Float)
    fun returnMapToCenter(mapView: FrameLayout, center: MapMarkerModel)
    fun clearMap()

    fun setOnCameraMoveListener(mapView: FrameLayout, callback: () -> Unit)
    fun setOnMapClickListener(mapView: FrameLayout, listener: OnMapClickListener)

    fun isMarkerVisible(mapView: FrameLayout, model: MapMarkerModel): Boolean
    fun isMarkerCenter(mapView: FrameLayout, model: MapMarkerModel): Boolean
    fun addMarker(
        model: MapMarkerModel,
        icon: Bitmap?,
        visible: Boolean,
        shouldHideIfCollision: Boolean,
        isSelectable: Boolean = false
    )

    fun selectMarker(markerName: String?, newIcon: Bitmap?)
    fun unSelectAllMarkers(markerFactory: MapMarkerFactory?)
    fun getSelectedMarkerName(): String?

    fun isZoomEqualTo(mapView: FrameLayout, zoomLevel: Float): Boolean
}
