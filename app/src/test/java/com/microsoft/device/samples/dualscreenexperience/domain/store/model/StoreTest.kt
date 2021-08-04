/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.store.model

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
}
