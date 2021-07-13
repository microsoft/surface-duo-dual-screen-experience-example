/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem
import com.microsoft.device.display.sampleheroapp.domain.catalog.usecases.GetCatalogListUseCase
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogListViewModel @Inject constructor(
    private val getCatalogListUseCase: GetCatalogListUseCase
) : ViewModel(), DataListProvider<CatalogItem> {
    var catalogItemList = MutableLiveData<List<CatalogItem>?>(null)

    init {
        viewModelScope.launch {
            catalogItemList.value = getCatalogListUseCase.getAll()
        }
    }

    override fun getDataList(): List<CatalogItem>? = catalogItemList.value
}