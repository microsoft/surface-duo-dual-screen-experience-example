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
import org.junit.Assert.assertThat
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
    fun getStoreImageEnumKey() {
        assertThat(StoreImage.JOY, iz(StoreImage.getEnumKey(JOY_KEY)))
        assertThat(StoreImage.CESAR, iz(StoreImage.getEnumKey(CESAR_KEY)))
        assertThat(StoreImage.HAKON, iz(StoreImage.getEnumKey(HAKON_KEY)))
        assertThat(StoreImage.BIANCA, iz(StoreImage.getEnumKey(BIANCA_KEY)))
        assertThat(StoreImage.GUY, iz(StoreImage.getEnumKey(GUY_KEY)))
        assertThat(StoreImage.CRISTIAN, iz(StoreImage.getEnumKey(CRISTIAN_KEY)))
        assertThat(StoreImage.MEHUL, iz(StoreImage.getEnumKey(MEHUL_KEY)))
    }
}
