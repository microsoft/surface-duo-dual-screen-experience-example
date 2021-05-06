/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.order

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.data.AppDatabase
import com.microsoft.device.display.sampleheroapp.data.order.local.OrderDao
import com.microsoft.device.display.sampleheroapp.data.product.local.ProductDao
import com.microsoft.device.display.sampleheroapp.data.product.productEntity
import com.microsoft.device.display.sampleheroapp.data.store.local.StoreDao
import com.microsoft.device.display.sampleheroapp.di.DatabaseModule
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS_ONE_ITEM
import com.microsoft.device.display.sampleheroapp.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED
import com.microsoft.device.display.sampleheroapp.presentation.order.OrderListAdapter.Companion.RECOMMENDATIONS_SIZE_ONE
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.unfreezeRotation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Singleton

@UninstallModules(DatabaseModule::class)
@HiltAndroidTest
class OrderNavigationSingleScreenTest : BaseNavigationOrderTest() {
    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Module
    @InstallIn(SingletonComponent::class)
    object TestDatabaseModule {

        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
            Room
                .inMemoryDatabaseBuilder(
                    appContext,
                    AppDatabase::class.java
                )
                .allowMainThreadQueries()
                .build()
                .also {
                    runBlocking {
                        it.productDao().insert(productEntity, productEntity, productEntity)
                    }
                }

        @Singleton
        @Provides
        fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()

        @Singleton
        @Provides
        fun provideStoreDao(database: AppDatabase): StoreDao = database.storeDao()

        @Singleton
        @Provides
        fun provideOrderDao(database: AppDatabase): OrderDao = database.orderDao()
    }

    @After
    fun resetOrientation() {
        unfreezeRotation()
    }

    @Test
    fun openEmptyOrderInPortraitMode() {
        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_ONE)
    }

    @Test
    fun openEmptyOrderInLandscapeMode() {
        setOrientationRight()

        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_ONE)
    }

    @Test
    fun addItemToOrderAndRemoveInPortraitMode() {
        addItemToOrderAndRemove(
            itemPosition = SINGLE_MODE_ORDER_ITEM_POS,
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_ONE,
            oneItemRecommendationsSize = RECOMMENDATIONS_SIZE_ONE,
            itemLiveData = activityRule.activity.getItemListLiveData()
        )
    }

    @Test
    fun addItemToOrderAndRemoveInLandscapeMode() {
        setOrientationRight()

        addItemToOrderAndRemove(
            itemPosition = SINGLE_MODE_ORDER_ITEM_POS,
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_ONE,
            oneItemRecommendationsSize = RECOMMENDATIONS_SIZE_ONE,
            itemLiveData = activityRule.activity.getItemListLiveData()
        )
    }

    @Test
    fun addItemToOrderAndSubmitInPortraitMode() {
        addItemToOrderAndSubmit(
            itemPosition = SINGLE_MODE_ORDER_ITEM_POS,
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_ONE,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }

    @Test
    fun addItemToOrderAndSubmitInLandscapeMode() {
        setOrientationRight()

        addItemToOrderAndSubmit(
            itemPosition = SINGLE_MODE_ORDER_ITEM_POS,
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_ONE,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }

    @Test
    fun addItemWithDifferentQuantitiesAndSubmitInPortraitMode() {
        addItemWithDifferentQuantitiesAndSubmit(
            itemPosition = SINGLE_MODE_ORDER_ITEM_POS,
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_ONE,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }

    @Test
    fun addItemWithDifferentQuantitiesAndSubmitInLandscapeMode() {
        setOrientationRight()

        addItemWithDifferentQuantitiesAndSubmit(
            itemPosition = SINGLE_MODE_ORDER_ITEM_POS,
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_ONE,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }
}
