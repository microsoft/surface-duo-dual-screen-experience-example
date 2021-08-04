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
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.data.AppDatabase
import com.microsoft.device.samples.dualscreenexperience.data.order.local.OrderDao
import com.microsoft.device.samples.dualscreenexperience.di.DatabaseModule
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkAboutInDualScreenMode
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkToolbarAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.about.openAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.checkToolbarDevItem
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.checkToolbarUserItem
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.openDevModeInDualMode
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.openUserMode
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS_ONE_ITEM
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.RECOMMENDATIONS_SIZE_THREE
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.RECOMMENDATIONS_SIZE_TWO
import com.microsoft.device.samples.dualscreenexperience.presentation.product.goBack
import com.microsoft.device.samples.dualscreenexperience.presentation.store.checkToolbar
import com.microsoft.device.samples.dualscreenexperience.util.setOrientationRight
import com.microsoft.device.samples.dualscreenexperience.util.switchFromSingleToDualScreen
import com.microsoft.device.samples.dualscreenexperience.util.unfreezeRotation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Singleton

@UninstallModules(DatabaseModule::class)
@HiltAndroidTest
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

        @Singleton
        @Provides
        fun provideOrderDao(database: AppDatabase): OrderDao = database.orderDao()
    }

    @After
    fun resetOrientation() {
        unfreezeRotation()
        ScreenManagerProvider.getScreenManager().clear()
    }

    @Test
    fun openEmptyOrderInDualPortraitMode() {
        switchFromSingleToDualScreen()

        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_THREE)
    }

    @Test
    fun openEmptyOrderInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_THREE)
    }

    @Test
    fun openAboutInDualPortraitMode() {
        switchFromSingleToDualScreen()

        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_THREE)

        checkToolbarAbout()
        openAbout()
        checkAboutInDualScreenMode()

        goBack()

        checkEmptyPage()
        scrollOrderToEnd()
        checkOrderRecommendationsPage(RECOMMENDATIONS_SIZE_THREE, POSITION_RECOMMENDATIONS)

        checkToolbar(R.string.toolbar_orders_title)
        checkToolbarAbout()
    }

    @Test
    fun openAboutInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_THREE)

        checkToolbarAbout()
        openAbout()
        checkAboutInDualScreenMode()

        goBack()

        checkEmptyPage()
        scrollOrderToEnd()
        checkOrderRecommendationsPage(RECOMMENDATIONS_SIZE_THREE, POSITION_RECOMMENDATIONS)

        checkToolbar(R.string.toolbar_orders_title)
        checkToolbarAbout()
    }

    @Test
    fun openDevModeInDualPortraitMode() {
        switchFromSingleToDualScreen()

        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_THREE)

        openDevModeInDualMode(hasDesignPattern = false)
        checkToolbarUserItem()

        openUserMode()

        checkEmptyPage()
        scrollOrderToEnd()
        checkOrderRecommendationsPage(RECOMMENDATIONS_SIZE_THREE, POSITION_RECOMMENDATIONS)

        checkToolbar(R.string.toolbar_orders_title)
        checkToolbarDevItem()
    }

    @Test
    fun openDevModeInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openEmptyOrders(recommendationsSize = RECOMMENDATIONS_SIZE_THREE)

        openDevModeInDualMode(hasDesignPattern = false)
        checkToolbarUserItem()

        openUserMode()

        checkEmptyPage()
        scrollOrderToEnd()
        checkOrderRecommendationsPage(RECOMMENDATIONS_SIZE_THREE, POSITION_RECOMMENDATIONS)

        checkToolbar(R.string.toolbar_orders_title)
        checkToolbarDevItem()
    }

    @Test
    fun addItemToOrderAndRemoveInDualPortraitMode() {
        switchFromSingleToDualScreen()

        addItemToOrderAndRemove(
            itemPosition = DUAL_PORTRAIT_ORDER_ITEM_POS,
            orderDetailsPosition = DUAL_PORTRAIT_ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_THREE,
            oneItemRecommendationsSize = RECOMMENDATIONS_SIZE_TWO,
            itemLiveData = activityRule.activity.getItemListLiveData()
        )
    }

    @Test
    fun addItemToOrderAndRemoveInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        addItemToOrderAndRemove(
            itemPosition = DUAL_LANDSCAPE_ORDER_ITEM_POS,
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_THREE,
            oneItemRecommendationsSize = RECOMMENDATIONS_SIZE_TWO,
            itemLiveData = activityRule.activity.getItemListLiveData()
        )
    }

    @Test
    fun addItemToOrderAndSubmitInDualPortraitMode() {
        switchFromSingleToDualScreen()

        addItemToOrderAndSubmit(
            itemPosition = DUAL_PORTRAIT_ORDER_ITEM_POS,
            orderDetailsPosition = DUAL_PORTRAIT_ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_THREE,
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
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_THREE,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }

    @Test
    fun addItemWithDifferentQuantitiesAndSubmitInDualPortraitMode() {
        switchFromSingleToDualScreen()

        addItemWithDifferentQuantitiesAndSubmit(
            itemPosition = DUAL_PORTRAIT_ORDER_ITEM_POS,
            orderDetailsPosition = DUAL_PORTRAIT_ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_THREE,
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
            orderDetailsPosition = ORDER_DETAILS_POS,
            recommendationsPosition = POSITION_RECOMMENDATIONS_ONE_ITEM_SUBMITTED,
            emptyRecommendationsSize = RECOMMENDATIONS_SIZE_THREE,
            itemLiveData = activityRule.activity.getItemListLiveData(),
            submittedOrderLiveData = activityRule.activity.getSubmittedOrderLiveData()
        )
    }
}
