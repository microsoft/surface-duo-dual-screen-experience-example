/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

interface DataListHandler<Model> : ItemClickListener<Model> {
    fun getDataList(): List<Model>?
    override fun onClick(model: Model?)
}
