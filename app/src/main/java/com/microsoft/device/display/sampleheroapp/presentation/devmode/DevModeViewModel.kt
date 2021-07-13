/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.devmode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    enum class DesignPattern(var path: String) {
        EXTENDED_CANVAS("extended-canvas"),
        LIST_DETAIL("list-detail"),
        DUAL_VIEW("dual-view"),
        TWO_PAGE("two-page"),
        COMPANION_PANE("companion-pane"),
        NONE("introduction#dual-screen-app-patterns");

        fun buildUrl() = "$DESIGN_PATTERN_BASE_URL/$path"

        companion object {
            fun get(path: String?) = values().first { it.path == path }
        }
    }

    enum class SdkComponent(var path: String) {
        BOTTOM_NAVIGATION_VIEW("bottomnavigation/surfaceduo-bottomnavigationview"),
        RECYCLER_VIEW("recyclerview/");

        fun buildUrl() = "$SDK_BASE_URL/$path$LANGUAGE_TAB"

        companion object {
            fun get(path: String?) = values().first { it.path == path }
        }
    }

    enum class AppScreen(var path: String) {
        STORES_MAP("ExtendCanvas/src/main/java/com/microsoft/device/display/samples/extendcanvas/ExtendedCanvasActivity.kt"),
        STORES_LIST("DualView/src/main/java/com/microsoft/device/display/samples/dualview/DualViewActivity.kt"),
        STORES_DETAILS("IntentToSecondScreen/src/main/java/com/microsoft/device/display/samples/intentsecondscreen/IntentToSecondScreenFirstActivity.kt"),
        PRODUCTS_CATALOG("TwoPage/src/main/java/com/microsoft/device/display/samples/twopage/TwoPageActivity.kt"),
        PRODUCTS_LIST_DETAILS("ListDetail/src/main/java/com/microsoft/device/display/samples/listdetail/ListDetailsActivity.kt"),
        PRODUCTS_CUSTOMIZE("CompanionPane/src/main/java/com/microsoft/device/display/samples/companionpane/CompanionPaneActivity.kt"),
        ORDERS("Qualifiers/src/main/java/com/microsoft/device/display/samples/qualifiers/QualifiersActivity.kt");

        fun buildUrl() = "$APP_BASE_URL/$path"

        companion object {
            fun get(path: String?) = values().first { it.path == path }
        }
    }

    companion object {
        const val DESIGN_PATTERN_BASE_URL = "https://docs.microsoft.com/en-us/dual-screen/design"
        const val SDK_BASE_URL = "https://docs.microsoft.com/en-us/dual-screen/android/api-reference/dualscreen-library"
        const val LANGUAGE_TAB = "?tabs=kotlin"
        const val APP_BASE_URL = "https://github.com/microsoft/surface-duo-sdk-samples-kotlin/blob/main"
    }
}
