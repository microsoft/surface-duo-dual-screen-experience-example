/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store.map

import com.microsoft.device.display.sampleheroapp.domain.store.model.MapMarkerModel
import com.microsoft.device.display.sampleheroapp.domain.store.model.MarkerType
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.cityGeopoint
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.cityMarkerModel
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeCenterMarkerModel
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeCityCenterMarkerModel
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeMarkerModel
import com.microsoft.maps.GeoboundingBox
import com.microsoft.maps.Geoposition
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class MapUtilsTest {

    @Test
    fun testToGeopoint() {
        assertThat(cityGeopoint.position.latitude, iz(cityMarkerModel.toGeopoint().position.latitude))
        assertThat(cityGeopoint.position.longitude, iz(cityMarkerModel.toGeopoint().position.longitude))
    }

    @Test
    fun testIsInBounds_whenLatDoesNotFit() {
        val box = GeoboundingBox(
            Geoposition(48.685, -122.129),
            Geoposition(47.647, -122.120)
        )
        assertFalse(box.isInBounds(cityMarkerModel))
    }

    @Test
    fun testIsInBounds_whenLngDoesNotFit() {
        val box = GeoboundingBox(
            Geoposition(47.638, -123.432),
            Geoposition(47.637, -124.543)
        )
        assertFalse(box.isInBounds(cityMarkerModel))
    }

    @Test
    fun testIsInBounds_whenBothLatLngFit() {
        val box = GeoboundingBox(
            Geoposition(47.638, -122.129),
            Geoposition(47.637, -122.120)
        )
        assertTrue(box.isInBounds(cityMarkerModel))
    }

    @Test
    fun testBuildCenterMarker_whenNoMarker() {
        val centerMarker = MapMarkerModel("", MarkerType.CENTER, 0.0, 0.0)
        assertThat(centerMarker, iz(buildCenterMarker(listOf())))
    }

    @Test
    fun testBuildCenterMarker_whenOnlyOneMarker() {
        assertThat(storeCenterMarkerModel, iz(buildCenterMarker(listOf(storeMarkerModel))))
    }

    @Test
    fun testBuildCenterMarker_whenMultipleMarkers() {
        assertThat(
            storeCityCenterMarkerModel,
            iz(buildCenterMarker(listOf(storeMarkerModel, cityMarkerModel)))
        )
    }
}
