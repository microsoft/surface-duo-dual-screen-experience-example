/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.devmode

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.device.samples.dualscreenexperience.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DevModeViewModel @Inject constructor(
    private val navigator: DevModeNavigator
) : ViewModel() {
    val webViewUrl = MutableLiveData(DESIGN_PATTERN_BASE_URL)

    var designPattern = DesignPattern.EXTENDED_CANVAS
    var sdkComponent = SdkComponent.BOTTOM_NAVIGATION_VIEW
    var appScreen = AppScreen.STORES_MAP

    var acceptedHosts = listOf("docs.microsoft.com")

    fun navigateToContent() {
        navigator.navigateToContent()
    }

    fun isNavigationAtContent() = navigator.isNavigationAtContent()

    fun navigateUp() {
        navigator.navigateUp()
    }

    fun loadDesignPatternPage() {
        webViewUrl.value = designPattern.buildUrl()
    }

    fun loadSdkComponentPage() {
        webViewUrl.value = sdkComponent.buildUrl()
    }

    fun loadAppScreenPage() {
        webViewUrl.value = appScreen.buildUrl()
    }

    enum class DesignPattern(var path: String, @StringRes var stringResId: Int) {
        EXTENDED_CANVAS("extended-canvas", R.string.dev_mode_extended_canvas),
        LIST_DETAIL("list-detail", R.string.dev_mode_list_detail),
        DUAL_VIEW("dual-view", R.string.dev_mode_dual_view),
        TWO_PAGE("two-page", R.string.dev_mode_two_page),
        COMPANION_PANE("companion-pane", R.string.dev_mode_companion_pane),
        NONE("introduction#dual-screen-app-patterns", 0);

        fun buildUrl() = "$DESIGN_PATTERN_BASE_URL/$path"

        companion object {
            fun get(path: String?) = values().first { it.path == path }
        }
    }

    enum class SdkComponent(var path: String) {
        BOTTOM_NAVIGATION_VIEW("bottomnavigation/surfaceduo-bottomnavigationview"),
        NAVIGATION("navigation-component/"),
        RECYCLER_VIEW("recyclerview/");

        fun buildUrl() = "$SDK_BASE_URL/$path$LANGUAGE_TAB"

        companion object {
            fun get(path: String?) = values().first { it.path == path }
        }
    }

    enum class AppScreen(var path: String) {
        STORES_MAP("store/map/StoreMapFragment.kt"),
        STORES_LIST("store/list/StoreListFragment.kt"),
        STORES_DETAILS("store/details/StoreDetailsFragment.kt"),
        CATALOG("catalog/CatalogListFragment.kt"),
        PRODUCTS_LIST_DETAILS("product/details/ProductDetailsFragment.kt"),
        PRODUCTS_CUSTOMIZE("product/customize/ProductCustomizeFragment.kt"),
        ORDER("order/OrderFragment.kt"),
        HISTORY_LIST_DETAILS("history/HistoryListFragment.kt");

        fun buildUrl() = "$APP_BASE_URL/$path"

        companion object {
            fun get(path: String?) = values().first { it.path == path }
        }
    }

    companion object {
        const val DESIGN_PATTERN_BASE_URL = "https://docs.microsoft.com/dual-screen/design"
        const val SDK_BASE_URL = "https://docs.microsoft.com/dual-screen/android/api-reference/dualscreen-library"
        const val LANGUAGE_TAB = "?tabs=kotlin"
        const val APP_BASE_URL = "https://github.com/microsoft/surface-duo-dual-screen-experience-example/blob/main/app/src/main/java/com/microsoft/device/samples/dualscreenexperience/presentation/"
    }
}
