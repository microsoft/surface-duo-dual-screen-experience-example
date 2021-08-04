/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MarkerType
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.cityGeopoint
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.cityMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeCenterMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeCityCenterMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeMarkerModel
import com.microsoft.maps.GeoboundingBox
import com.microsoft.maps.Geoposition
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class MapUtilsTest {

    @Test
    fun toGeopoint() {
        assertThat(cityGeopoint.position.latitude, iz(cityMarkerModel.toGeopoint().position.latitude))
        assertThat(cityGeopoint.position.longitude, iz(cityMarkerModel.toGeopoint().position.longitude))
    }

    @Test
    fun isInBoundsWhenLatDoesNotFit() {
        val box = GeoboundingBox(
            Geoposition(48.685, -122.129),
            Geoposition(47.647, -122.120)
        )
        assertFalse(box.isInBounds(cityMarkerModel))
    }

    @Test
    fun isInBoundsWhenLngDoesNotFit() {
        val box = GeoboundingBox(
            Geoposition(47.638, -123.432),
            Geoposition(47.637, -124.543)
        )
        assertFalse(box.isInBounds(cityMarkerModel))
    }

    @Test
    fun isInBoundsWhenBothLatLngFit() {
        val box = GeoboundingBox(
            Geoposition(47.673, -122.136),
            Geoposition(47.672, -122.135)
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