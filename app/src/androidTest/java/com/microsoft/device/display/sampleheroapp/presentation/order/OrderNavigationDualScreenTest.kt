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
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.switchFromSingleToDualScreen
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
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Singleton

@UninstallModules(DatabaseModule::class)
@HiltAndroidTest
@Ignore("Needs Navigation Component support")
class OrderNavigationDualScreenTest : BaseNavigationOrderTest() {
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
    fun openEmptyOrderInDualPortraitMode() {
        switchFromSingleToDualScreen()

        openEmptyOrders(recommendationsSize = DUAL_MODE_RECOMMENDATIONS_SIZE)
    }

    @Test
    fun openEmptyOrderInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openEmptyOrders(recommendationsSize = DUAL_MODE_RECOMMENDATIONS_SIZE)
    }

    @Test
    fun addItemToOrderAndRemoveInDualPortraitMode() {
        switchFromSingleToDualScreen()

        addItemToOrderAndRemove(
            itemPosition = DUAL_PORTRAIT_ORDER_ITEM_POS,
            recommendationsSize = DUAL_MODE_RECOMMENDATIONS_SIZE,
            itemLiveData = activityRule.activity.getItemListLiveData()
        )
    }

    @Test
    fun addItemToOrderAndRemoveInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        addItemToOrderAndRemove(
            itemPosition = DUAL_LANDSCAPE_ORDER_ITEM_POS,
            recommendationsSize = DUAL_MODE_RECOMMENDATIONS_SIZE,
            itemLiveData = activityRule.activity.getItemListLiveData()
        )
    }

    @Test
    fun addItemToOrderAndSubmitInDualPortraitMode() {
        switchFromSingleToDualScreen()

        addItemToOrderAndSubmit(
            itemPosition = DUAL_PORTRAIT_ORDER_ITEM_POS,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }

    @Test
    fun addItemToOrderAndSubmitInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        addItemToOrderAndSubmit(
            itemPosition = DUAL_LANDSCAPE_ORDER_ITEM_POS,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }

    @Test
    fun addItemWithDifferentQuantitiesAndSubmitInDualPortraitMode() {
        switchFromSingleToDualScreen()

        addItemWithDifferentQuantitiesAndSubmit(
            itemPosition = DUAL_PORTRAIT_ORDER_ITEM_POS,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }

    @Test
    fun addItemWithDifferentQuantitiesAndSubmitInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        addItemWithDifferentQuantitiesAndSubmit(
            itemPosition = DUAL_LANDSCAPE_ORDER_ITEM_POS,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }
}
