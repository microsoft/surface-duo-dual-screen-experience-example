/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.store.model

import com.microsoft.device.display.sampleheroapp.domain.store.testutil.city
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.cityEntity
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.cityMarkerModel
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class CityTest {

    @Test
    fun buildFromCityEntity() {
        assertThat(city, iz(City(cityEntity)))
    }

    @Test
    fun toMapMarkerModel() {
        assertThat(cityMarkerModel, iz(city.toMapMarkerModel()))
    }
}
