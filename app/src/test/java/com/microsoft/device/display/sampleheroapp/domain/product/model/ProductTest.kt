/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.product.model

import com.microsoft.device.display.sampleheroapp.domain.product.testutil.AQUA_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.BLACK_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.BLUE_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.CLASSIC_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.DARK_RED_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.EXPLORER_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.GRAY_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.MUSICLANDER_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.MUSTARD_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.ORANGE_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.RED_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.WARLOCK_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.WHITE_KEY
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.product
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.productEntity
import org.junit.Assert.assertThat
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class ProductTest {

    @Test
    fun buildFromProductEntity() {
        assertThat(product, iz(Product(productEntity)))
    }

    @Test
    fun getProductTypeUsingKey() {
        assertThat(ProductType.WARLOCK, iz(ProductType.get(WARLOCK_KEY)))
        assertThat(ProductType.CLASSIC, iz(ProductType.get(CLASSIC_KEY)))
        assertThat(ProductType.EXPLORER, iz(ProductType.get(EXPLORER_KEY)))
        assertThat(ProductType.MUSICLANDER, iz(ProductType.get(MUSICLANDER_KEY)))
    }

    @Test
    fun getProductColorUsingKey() {
        assertThat(ProductColor.DARK_RED, iz(ProductColor.get(DARK_RED_KEY)))
        assertThat(ProductColor.GRAY, iz(ProductColor.get(GRAY_KEY)))
        assertThat(ProductColor.BLUE, iz(ProductColor.get(BLUE_KEY)))
        assertThat(ProductColor.WHITE, iz(ProductColor.get(WHITE_KEY)))
        assertThat(ProductColor.ORANGE, iz(ProductColor.get(ORANGE_KEY)))
        assertThat(ProductColor.AQUA, iz(ProductColor.get(AQUA_KEY)))
        assertThat(ProductColor.BLACK, iz(ProductColor.get(BLACK_KEY)))
        assertThat(ProductColor.MUSTARD, iz(ProductColor.get(MUSTARD_KEY)))
        assertThat(ProductColor.RED, iz(ProductColor.get(RED_KEY)))
    }
}
