/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MarkerType
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.cityMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeCenterMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeCityCenterMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeMarkerModel
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class MapUtilsTest {

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
