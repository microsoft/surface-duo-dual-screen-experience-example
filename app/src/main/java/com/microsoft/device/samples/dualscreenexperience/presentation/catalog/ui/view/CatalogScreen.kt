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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.CatalogListViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.PagerState
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
    viewModel: CatalogListViewModel = viewModel()
) {
    val catalogList = viewModel.catalogItemList.observeAsState()
    // Calculate page text width based on the smallest pane width
    val pageTextWidth = min(pane1WidthDp, pane2WidthDp)

    // Calculate the necessary page padding, based on pane width differences and fold width
    val pagePadding = abs(pane1WidthDp.value - pane2WidthDp.value).dp + foldSizeDp

    val pages = setupPages(
        pageTextWidth, pagePadding,
        catalogList.value ?: listOf(), isFeatureHorizontal, isSinglePortrait, isDualScreen
    )
    PageViews(pages, isDualScreen)
}

@Composable
fun PageViews(pages: List<@Composable () -> Unit>, isDualScreen: Boolean) {
    val maxPage = (pages.size - 1).coerceAtLeast(0)
    val pagerState: PagerState =
        remember { PagerState(currentPage = 0, minPage = 0, maxPage = maxPage) }
    pagerState.isDualMode = isDualScreen

    ViewPager(
        state = pagerState,
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
    isDualScreen: Boolean
): List<@Composable () -> Unit> {
    val modifier = if (pageTextWidth.value != 0f) Modifier
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
                isFeatureHorizontal = isFeatureHorizontal
            )
        },
        {
            CatalogThirdPage(
                modifier = modifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal,
                isSinglePortrait = isSinglePortrait
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
    )
}
