/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.PagerState
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.PagerStateSaver
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.ViewPager
import kotlin.math.abs

@Composable
fun Catalog(
    pane1WidthDp: Dp,
    pane2WidthDp: Dp,
    isDualScreen: Boolean,
    foldSizeDp: Dp,
    isFeatureHorizontal: Boolean,
    isSinglePortrait: Boolean,
    showTwoPages: Boolean,
    showSmallWindowWidthLayout: Boolean,
    isFoldStateHalfOpened: Boolean,
    catalogList: List<CatalogItem>
) {

    // Calculate page text width based on the smallest pane width
    val pageTextWidth = min(pane1WidthDp, pane2WidthDp)

    // Calculate the necessary page padding, based on pane width differences and fold width
    val pagePadding = abs(pane1WidthDp.value - pane2WidthDp.value).dp + foldSizeDp

    val pages = setupPages(
        pageTextWidth,
        pagePadding,
        catalogList,
        isFeatureHorizontal,
        isSinglePortrait,
        isDualScreen,
        showTwoPages,
        showSmallWindowWidthLayout,
        isFoldStateHalfOpened
    )
    PageViews(pages, isDualScreen, showTwoPages)
}

@Composable
fun PageViews(pages: List<@Composable () -> Unit>, isDualScreen: Boolean, showTwoPages: Boolean) {
    val maxPage = (pages.size - 1).coerceAtLeast(0)
    val pagerState: MutableState<PagerState> =
        rememberSaveable(stateSaver = PagerStateSaver) {
            mutableStateOf(
                PagerState(
                    currentPage = 0,
                    minPage = 0,
                    maxPage = maxPage
                )
            )
        }
    pagerState.value.isDualMode = isDualScreen && showTwoPages

    ViewPager(
        state = pagerState.value,
        modifier = Modifier.fillMaxSize()
    ) {
        pages[page]()
    }
}

private fun setupPages(
    pageTextWidth: Dp,
    pagePadding: Dp = 0.dp,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean,
    isSinglePortrait: Boolean,
    isDualScreen: Boolean,
    showTwoPages: Boolean,
    showSmallWindowWidthLayout: Boolean,
    isFoldStateHalfOpened: Boolean
): List<@Composable () -> Unit> {
    val modifier = if (pageTextWidth.value != 0f && showTwoPages) Modifier
        .padding(end = pagePadding)
        .width(pageTextWidth)
        .fillMaxHeight()
        .clipToBounds() else Modifier.fillMaxSize()
    return listOf<@Composable () -> Unit>(
        {
            CatalogFirstPage(modifier = modifier, catalogList = catalogList)
        },
        {
            CatalogSecondPage(
                modifier = modifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal,
                showTwoPages = showTwoPages,
                isSmallWindowWidth = showSmallWindowWidthLayout
            )
        },
        {
            CatalogThirdPage(
                modifier = modifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal,
                isSinglePortrait = isSinglePortrait,
                showTwoPages = showTwoPages,
                isSmallWindowWidth = showSmallWindowWidthLayout,
                isFoldStateHalfOpened = isFoldStateHalfOpened
            )
        },
        {
            CatalogFourthPage(
                modifier = modifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal
            )
        },
        {
            CatalogFifthPage(
                modifier = modifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal,
                isDualScreen = isDualScreen
            )
        },
        {
            CatalogSixthPage(
                modifier = modifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal
            )
        },
        {
            CatalogSeventhPage(
                modifier = modifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal
            )
        },
    )
}
