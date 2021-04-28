/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.display.sampleheroapp.domain.order.model.OrderItem
import com.microsoft.device.display.sampleheroapp.domain.order.usecases.AddItemToOrderUseCase
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductType
import com.microsoft.device.display.sampleheroapp.domain.product.usecases.GetProductsUseCase
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class OrderRecommendationsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addItemUseCase: AddItemToOrderUseCase
) : ViewModel(), DataListHandler<Product> {
    var productList = MutableLiveData<List<Product>?>(null)
    private var recommendationsList = MutableLiveData<List<Product>?>(null)

    init {
        viewModelScope.launch {
            productList.value = getProductsUseCase.getAll()
        }
    }

    fun refreshRecommendationList() {
        recommendationsList.value = generateRecommendationList(RECOMMENDATIONS_COUNT)
    }

    private fun updateOrder(product: Product) {
        viewModelScope.launch {
            addItemUseCase.addToOrder(OrderItem(product))
        }
    }

    private fun generateRecommendationList(size: Int): List<Product>? =
        productList.value?.takeIf { it.isNotEmpty() }?.let { products ->
            mutableListOf<Product>().apply {
                val shuffledShapes = ProductType.values().toList().shuffled()

                for (index in 0 until size) {
                    products[Random.nextInt(0, products.size)].copy(
                        bodyShape = shuffledShapes[index],
                        color = shuffledShapes[index].colorList[Random.nextInt(0, shuffledShapes[index].colorList.size)]
                    ).also {
                        add(it)
                    }
                }
            }
        }

    override fun getDataList(): List<Product>? = recommendationsList.value

    override fun onClick(model: Product?) {
        model?.let { updateOrder(it) }
    }

    companion object {
        var RECOMMENDATIONS_COUNT = 3
    }
}
