/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

interface QuantityDataListHandler<Model> {
    fun getDataList(): List<Model>?
    fun updateQuantity(model: Model?, newValue: Int)
}
