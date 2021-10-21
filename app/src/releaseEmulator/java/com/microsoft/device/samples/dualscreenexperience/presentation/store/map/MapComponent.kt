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
import com.microsoft.device.samples.dualscreenexperience.BuildConfig
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.LAT_INIT
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.LNG_INIT
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig.ZOOM_LEVEL_CITY
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.maps.Geopoint
import com.microsoft.maps.MapAnimationKind
import com.microsoft.maps.MapElementCollisionBehavior
import com.microsoft.maps.MapElementLayer
import com.microsoft.maps.MapIcon
import com.microsoft.maps.MapImage
import com.microsoft.maps.MapLoadingStatus
import com.microsoft.maps.MapProjection
import com.microsoft.maps.MapRenderMode
import com.microsoft.maps.MapScene.createFromLocationAndZoomLevel
import com.microsoft.maps.MapStyleSheets
import com.microsoft.maps.MapView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MapComponent @Inject constructor() : IMapComponent {

    private var mapLayer: MapElementLayer? = null
    private var selectableMarkerMap: HashMap<String, MapIconSelectable> = HashMap()

    override fun shouldShowNoGmsMessage(context: Context): Boolean = false

    override fun generateMapView(context: Context): FrameLayout =
        MapView(context, MapRenderMode.RASTER).also {
            it.setCredentialsKey(BuildConfig.BING_MAPS_KEY)
            it.setScene(
                createFromLocationAndZoomLevel(Geopoint(LAT_INIT, LNG_INIT), ZOOM_LEVEL_CITY.toDouble()),
                MapAnimationKind.NONE
            )
        }

    override suspend fun generateController(mapView: FrameLayout) {
        mapLayer = MapElementLayer()
        mapLayer?.let { mapElementLayer ->
            (mapView as? MapView)?.layers?.add(mapElementLayer)
        }
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
        mapLayer = null
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
        (mapView as? MapView)?.apply {
            mapProjection = MapProjection.WEB_MERCATOR
            mapStyleSheet = MapStyleSheets.roadDark()
            userInterfaceOptions.apply {
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
    }

    override fun resetMapWithAnimations(mapView: FrameLayout, center: MapMarkerModel, zoomLevel: Float) {
        (mapView as? MapView)?.setScene(
            createFromLocationAndZoomLevel(
                center.toGeopoint(),
                zoomLevel.toDouble()
            ),
            MapAnimationKind.DEFAULT
        )
    }

    override fun resetMapWithoutAnimations(mapView: FrameLayout, center: MapMarkerModel, zoomLevel: Float) {
        (mapView as? MapView)?.setScene(
            createFromLocationAndZoomLevel(
                center.toGeopoint(),
                zoomLevel.toDouble()
            ),
            MapAnimationKind.NONE
        )
    }

    override fun returnMapToCenter(mapView: FrameLayout, center: MapMarkerModel) {
        (mapView as? MapView)?.panTo(center.toGeopoint())
    }

    override fun clearMap() {
        mapLayer?.elements?.clear()
    }

    override fun setOnCameraMoveListener(mapView: FrameLayout, callback: () -> Unit) {
        (mapView as? MapView)?.addOnMapCameraChangedListener {
            callback.invoke()
            true
        }
    }

    override fun setOnMapClickListener(mapView: FrameLayout, listener: OnMapClickListener) {
        (mapView as? MapView)?.let { map ->
            map.addOnMapTappedListener { args ->
                if (!isMapLoading(map)) {
                    val mapIcon = map.findMapElementsAtOffset(args.position).firstOrNull()

                    if (mapIcon != null) {
                        val isAlreadySelected = selectableMarkerMap.values
                            .firstOrNull { mapIcon.tag == it.bingModel.tag }?.isSelected ?: false

                        listener.onMarkerClicked(mapIcon.tag.toString(), isAlreadySelected)
                    } else {
                        listener.onNoMarkerClicked()
                    }

                    true
                } else {
                    listener.onNoMarkerClicked()
                    false
                }
            }
        }
    }

    private fun isMapLoading(mapView: MapView?) =
        mapView?.loadingStatus == MapLoadingStatus.UPDATING ||
            mapView?.loadingStatus == MapLoadingStatus.UPDATING_WITH_BASICS_COMPLETE

    override fun isMarkerVisible(mapView: FrameLayout, model: MapMarkerModel): Boolean =
        (mapView as? MapView)?.bounds?.isInBounds(model) == true

    override fun isMarkerCenter(mapView: FrameLayout, model: MapMarkerModel): Boolean =
        (mapView as? MapView)?.mapCamera?.location == model.toGeopoint()

    override fun addMarker(
        model: MapMarkerModel,
        icon: Bitmap?,
        visible: Boolean,
        shouldHideIfCollision: Boolean,
        isSelectable: Boolean
    ) {
        val element = MapIcon().apply {
            location = model.toGeopoint()
            tag = model.name
            contentDescription = model.name
            image = icon?.let { MapImage(it) }
            if (shouldHideIfCollision) {
                desiredCollisionBehavior = MapElementCollisionBehavior.HIDE
            }
        }
        if (isSelectable) {
            selectableMarkerMap[model.name] =
                MapIconSelectable(
                    element,
                    false
                )
        }
        mapLayer?.elements?.add(element)
    }

    override fun selectMarker(markerName: String?, newIcon: Bitmap?) {
        newIcon?.let {
            selectableMarkerMap[markerName]?.bingModel?.image = MapImage(it)
            selectableMarkerMap[markerName]?.isSelected = true
        }
    }

    override fun unSelectAllMarkers(markerFactory: MapMarkerFactory?) {
        selectableMarkerMap.keys
            .filter { selectableMarkerMap[it]?.isSelected ?: false }
            .forEach { key ->
                markerFactory?.let { factory ->
                    selectableMarkerMap[key]?.bingModel?.image =
                        MapImage(factory.createBitmapWithText(key))
                    selectableMarkerMap[key]?.isSelected = false
                }
            }
    }

    override fun getSelectedMarkerName(): String? =
        selectableMarkerMap
            .keys
            .firstOrNull { selectableMarkerMap[it]?.isSelected ?: false }

    override fun isZoomEqualTo(mapView: FrameLayout, zoomLevel: Float): Boolean =
        (mapView as? MapView)?.zoomLevel == zoomLevel.toDouble()
}
