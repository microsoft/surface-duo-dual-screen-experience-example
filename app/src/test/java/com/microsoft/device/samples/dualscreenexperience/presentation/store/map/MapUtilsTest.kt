/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MarkerType
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.cityGeopoint
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.cityMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeCenterMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeCityCenterMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeMarkerModel
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class MapUtilsTest {

    @Test
    fun toLatLng() {
        assertThat(cityGeopoint.latitude, iz(cityMarkerModel.toLatLng().latitude))
        assertThat(cityGeopoint.longitude, iz(cityMarkerModel.toLatLng().longitude))
    }

    @Test
    fun isInBoundsWhenLatDoesNotFit() {
        val box = LatLngBounds(
            LatLng(47.647, -122.129),
            LatLng(48.685, -122.120)
        )
        assertFalse(box.isInBounds(cityMarkerModel))
    }

    @Test
    fun isInBoundsWhenLngDoesNotFit() {
        val box = LatLngBounds(
            LatLng(47.637, -124.432),
            LatLng(47.638, -123.543)
        )
        assertFalse(box.isInBounds(cityMarkerModel))
    }

    @Test
    fun isInBoundsWhenBothLatLngFit() {
        val box = LatLngBounds(
            LatLng(47.620, -122.136),
            LatLng(47.621, -122.135)
        )
        assertTrue(box.isInBounds(cityMarkerModel))
    }

    @Test
    fun buildCenterMarkerWhenNoMarker() {
        val centerMarker = MapMarkerModel("", MarkerType.CENTER, 0.0, 0.0)
        assertThat(centerMarker, iz(buildCenterMarker(listOf())))
    }

    @Test
    fun buildCenterMarkerWhenOnlyOneMarker() {
        assertThat(storeCenterMarkerModel, iz(buildCenterMarker(listOf(storeMarkerModel))))
    }

    @Test
    fun buildCenterMarkerWhenMultipleMarkers() {
        assertThat(
            storeCityCenterMarkerModel,
            iz(buildCenterMarker(listOf(storeMarkerModel, cityMarkerModel)))
        )
    }
}
