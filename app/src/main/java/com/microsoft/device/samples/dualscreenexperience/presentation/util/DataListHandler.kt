/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

interface DataListHandler<Model> : DataListProvider<Model>, ItemClickListener<Model> {
    override fun getDataList(): List<Model>?
    override fun onClick(model: Model?)
}
