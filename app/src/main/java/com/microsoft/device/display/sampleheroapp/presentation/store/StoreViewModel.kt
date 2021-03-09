/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.display.sampleheroapp.domain.store.model.MapMarkerModel
import com.microsoft.device.display.sampleheroapp.domain.store.model.Store
import com.microsoft.device.display.sampleheroapp.domain.store.usecases.GetMapMarkersUseCase
import com.microsoft.device.display.sampleheroapp.domain.store.usecases.GetStoresByCityUseCase
import com.microsoft.device.display.sampleheroapp.domain.store.usecases.GetStoresUseCase
import com.microsoft.device.display.sampleheroapp.presentation.store.map.buildCenterMarker
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val getMapMarkersUseCase: GetMapMarkersUseCase,
    private val getStoresByCityUseCase: GetStoresByCityUseCase,
    private val getStoresUseCase: GetStoresUseCase,
    private val navigator: StoreNavigator
) : ViewModel(), DataListHandler<Store> {

    val visibleStoresList = MutableLiveData<List<Store>?>(null)
    val markersList = MutableLiveData<List<MapMarkerModel>?>(null)
    val markersCenter = MutableLiveData<MapMarkerModel>(null)
    val selectedStore = MutableLiveData<Store?>(null)
    val selectedCity = MutableLiveData<MapMarkerModel?>(null)

    init {
        viewModelScope.launch {
            markersList.value = getMapMarkersUseCase.getAll()
            resetMap()
        }
    }

    private fun resetMap() {
        if (selectedCity.value != null) {
            selectedCity.value = null
        }
        markersList.value?.takeIf { it.isNotEmpty() }?.let {
            markersCenter.value = buildCenterMarker(markersList.value!!)
        }
    }

    override fun getDataList(): List<Store>? = visibleStoresList.value

    override fun onClick(model: Store?) {
        if (selectedStore.value == null) {
            navigator.navigateToStoreDetailsFromList()
        }
        selectedStore.value = model
    }

    fun navigateToDetails(markerModel: MapMarkerModel) {
        if (selectedStore.value == null) {
            if (selectedCity.value == null) {
                navigator.navigateToStoreDetailsFromMap()
            } else {
                navigator.navigateToStoreDetailsFromList()
            }
        }
        viewModelScope.launch {
            selectedStore.value = getStoresUseCase.getById(markerModel.id)
        }
    }

    fun navigateToList(city: MapMarkerModel) {
        selectedCity.value = city
        viewModelScope.launch {
            visibleStoresList.value = getStoresByCityUseCase.getAll(city.name)
            visibleStoresList.value?.takeIf { it.isNotEmpty() }?.let { cityStores ->
                markersCenter.value = buildCenterMarker(cityStores.map { it.toMapMarkerModel() })
            }
        }
        navigator.navigateToStoreList()
    }

    fun updateStoreList(storeIds: List<Long>) {
        viewModelScope.launch {
            visibleStoresList.value = getStoresUseCase.getAll().filter { it.storeId in storeIds }
        }
    }

    fun navigateUp() {
        navigator.navigateUp()
        if (selectedStore.value != null) {
            selectedStore.value = null
        } else {
            resetMap()
        }
    }
}
