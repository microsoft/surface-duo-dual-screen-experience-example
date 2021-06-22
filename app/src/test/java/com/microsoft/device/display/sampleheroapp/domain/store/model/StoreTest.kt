/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.store.model

import com.microsoft.device.display.sampleheroapp.domain.store.testutil.BIANCA_KEY
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.CESAR_KEY
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.CRISTIAN_KEY
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.GUY_KEY
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.HAKON_KEY
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.JOY_KEY
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.MEHUL_KEY
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.store
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeEntity
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeMarkerModel
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class StoreTest {

    @Test
    fun buildFromStoreEntity() {
        assertThat(store, iz(Store(storeEntity)))
    }

    @Test
    fun toMapMarkerModel() {
        assertThat(storeMarkerModel, iz(store.toMapMarkerModel()))
    }

    @Test
    fun getStoreImageUsingKey() {
        assertThat(StoreImage.JOY, iz(StoreImage.get(JOY_KEY)))
        assertThat(StoreImage.CESAR, iz(StoreImage.get(CESAR_KEY)))
        assertThat(StoreImage.HAKON, iz(StoreImage.get(HAKON_KEY)))
        assertThat(StoreImage.BIANCA, iz(StoreImage.get(BIANCA_KEY)))
        assertThat(StoreImage.GUY, iz(StoreImage.get(GUY_KEY)))
        assertThat(StoreImage.CRISTIAN, iz(StoreImage.get(CRISTIAN_KEY)))
        assertThat(StoreImage.MEHUL, iz(StoreImage.get(MEHUL_KEY)))
    }
}
