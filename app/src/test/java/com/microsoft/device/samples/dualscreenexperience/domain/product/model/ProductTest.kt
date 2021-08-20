/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.product.model

import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.AQUA_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.BLUE_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.CLASSIC_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.DARK_RED_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.ELECTRIC_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.GRAY_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.HARDROCK_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.LIGHT_GRAY_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.MUSTARD_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.ORANGE_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.RED_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.ROCK_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.WHITE_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.YELLOW_KEY
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.product
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.productEntity
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class ProductTest {

    @Test
    fun buildFromProductEntity() {
        assertThat(product, iz(Product(productEntity)))
    }

    @Test
    fun getProductTypeUsingKey() {
        assertThat(ProductType.ROCK, iz(ProductType.get(ROCK_KEY)))
        assertThat(ProductType.CLASSIC, iz(ProductType.get(CLASSIC_KEY)))
        assertThat(ProductType.ELECTRIC, iz(ProductType.get(ELECTRIC_KEY)))
        assertThat(ProductType.HARDROCK, iz(ProductType.get(HARDROCK_KEY)))
    }

    @Test
    fun getProductColorUsingKey() {
        assertThat(ProductColor.DARK_RED, iz(ProductColor.get(DARK_RED_KEY)))
        assertThat(ProductColor.GRAY, iz(ProductColor.get(GRAY_KEY)))
        assertThat(ProductColor.BLUE, iz(ProductColor.get(BLUE_KEY)))
        assertThat(ProductColor.YELLOW, iz(ProductColor.get(YELLOW_KEY)))
        assertThat(ProductColor.WHITE, iz(ProductColor.get(WHITE_KEY)))
        assertThat(ProductColor.ORANGE, iz(ProductColor.get(ORANGE_KEY)))
        assertThat(ProductColor.AQUA, iz(ProductColor.get(AQUA_KEY)))
        assertThat(ProductColor.LIGHT_GRAY, iz(ProductColor.get(LIGHT_GRAY_KEY)))
        assertThat(ProductColor.MUSTARD, iz(ProductColor.get(MUSTARD_KEY)))
        assertThat(ProductColor.RED, iz(ProductColor.get(RED_KEY)))
    }
}
