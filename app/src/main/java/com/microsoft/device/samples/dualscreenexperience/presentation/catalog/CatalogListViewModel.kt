/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.usecases.GetCatalogListUseCase
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogListViewModel @Inject constructor(
    private val getCatalogListUseCase: GetCatalogListUseCase
) : ViewModel(), DataListProvider<CatalogItem> {
    var catalogItemList = MutableLiveData<List<CatalogItem>?>(null)
    var catalogItemPosition = MutableLiveData(0)

    private val _showTwoPages = MutableLiveData(false)
    val showTwoPages: LiveData<Boolean>
        get() = _showTwoPages

    private val _showSmallWindowWidthLayout = MutableLiveData(false)
    val showSmallWindowWidthLayout: LiveData<Boolean>
        get() = _showSmallWindowWidthLayout

    init {
        viewModelScope.launch {
            catalogItemList.value = getCatalogListUseCase.getAll()
        }
    }

    fun updateShowTwoPages(showTwoPages: Boolean) {
        _showTwoPages.value = showTwoPages
    }

    fun updateShowSmallWindowWidthLayout(showSmallWindowWidthLayout: Boolean) {
        _showSmallWindowWidthLayout.value = showSmallWindowWidthLayout
    }

    override fun getDataList(): List<CatalogItem>? = catalogItemList.value
}
