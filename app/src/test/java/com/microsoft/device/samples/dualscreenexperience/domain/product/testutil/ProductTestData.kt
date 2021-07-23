/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.product.testutil

import com.microsoft.device.samples.dualscreenexperience.data.product.model.ProductEntity
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType

val productEntity = ProductEntity(
    1,
    "EG - 29387 Wood",
    6495,
    "Wood body with gloss finish, Three Player Series pickups," +
        "9.5\"-radius fingerboard," +
        "2-point tremolo bridge",
    3.1f,
    21,
    2,
    5
)

val secondProductEntity = ProductEntity(
    2,
    "EG - 18275 Metal body",
    4323,
    "Metal body with gloss finish, Three Player Series pickups," +
        "9.5\"-radius fingerboard," +
        "2-point tremolo bridge",
    4.0f,
    18,
    1,
    1
)

val product = Product(
    1,
    "EG - 29387 Wood",
    6495,
    "Wood body with gloss finish, Three Player Series pickups," +
        "9.5\"-radius fingerboard," +
        "2-point tremolo bridge",
    3.1f,
    21,
    ProductType.CLASSIC,
    ProductColor.ORANGE
)

val secondProduct = Product(
    2,
    "EG - 18275 Metal body",
    4323,
    "Metal body with gloss finish, Three Player Series pickups," +
        "9.5\"-radius fingerboard," +
        "2-point tremolo bridge",
    4.0f,
    18,
    ProductType.WARLOCK,
    ProductColor.DARK_RED
)

const val WARLOCK_KEY = 1
const val CLASSIC_KEY = 2
const val EXPLORER_KEY = 3
const val MUSICLANDER_KEY = 4

const val DARK_RED_KEY = 1
const val GRAY_KEY = 2
const val BLUE_KEY = 3
const val WHITE_KEY = 4
const val ORANGE_KEY = 5
const val AQUA_KEY = 6
const val BLACK_KEY = 7
const val MUSTARD_KEY = 8
const val RED_KEY = 9
