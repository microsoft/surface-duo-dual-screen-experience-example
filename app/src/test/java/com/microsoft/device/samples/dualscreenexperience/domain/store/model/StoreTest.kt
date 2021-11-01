/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.store.model

import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.ANA_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.BEN_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.KRISTIAN_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.NATASHA_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.OVE_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.QUINN_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.SERGIO_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.store
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeEntity
import com.microsoft.device.samples.dualscreenexperience.domain.store.testutil.storeMarkerModel
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
        assertThat(StoreImage.ANA, iz(StoreImage.get(ANA_KEY)))
        assertThat(StoreImage.SERGIO, iz(StoreImage.get(SERGIO_KEY)))
        assertThat(StoreImage.OVE, iz(StoreImage.get(OVE_KEY)))
        assertThat(StoreImage.NATASHA, iz(StoreImage.get(NATASHA_KEY)))
        assertThat(StoreImage.BEN, iz(StoreImage.get(BEN_KEY)))
        assertThat(StoreImage.KRISTIAN, iz(StoreImage.get(KRISTIAN_KEY)))
        assertThat(StoreImage.QUINN, iz(StoreImage.get(QUINN_KEY)))
    }
}
