/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.rule.ActivityTestRule
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.data.AppDatabase
import com.microsoft.device.samples.dualscreenexperience.data.order.local.OrderDao
import com.microsoft.device.samples.dualscreenexperience.data.product.local.ProductDao
import com.microsoft.device.samples.dualscreenexperience.data.product.productEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.local.StoreDao
import com.microsoft.device.samples.dualscreenexperience.di.DatabaseModule
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkAboutInSingleScreenMode
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkToolbarAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.about.openAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS_ONE_ITEM
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.RECOMMENDATIONS_SIZE_ONE
import com.microsoft.device.samples.dualscreenexperience.presentation.product.goBack
import com.microsoft.device.samples.dualscreenexperience.presentation.store.checkToolbar
import com.microsoft.device.samples.dualscreenexperience.util.setOrientationRight
import com.microsoft.device.samples.dualscreenexperience.util.unfreezeRotation
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
    fun openAboutInPortraitMode() {
        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_ONE)

        checkToolbarAbout()
        openAbout()
        checkAboutInSingleScreenMode()

        goBack()

        checkEmptyPage()
        scrollOrderToEnd()
        checkOrderRecommendationsPage(RECOMMENDATIONS_SIZE_ONE, POSITION_RECOMMENDATIONS)

        checkToolbar(R.string.toolbar_orders_title)
        checkToolbarAbout()
    }

    @Test
    fun openAboutInLandscapeMode() {
        setOrientationRight()

        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_ONE)

        checkToolbarAbout()
        openAbout()
        checkAboutInSingleScreenMode()

        goBack()

        checkEmptyPage()
        scrollOrderToEnd()
        checkOrderRecommendationsPage(RECOMMENDATIONS_SIZE_ONE, POSITION_RECOMMENDATIONS)

        checkToolbar(R.string.toolbar_orders_title)
        checkToolbarAbout()
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
