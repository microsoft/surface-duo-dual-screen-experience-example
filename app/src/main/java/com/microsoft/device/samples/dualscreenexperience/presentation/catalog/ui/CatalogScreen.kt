/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.PagerState
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.ViewPager
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.rememberViewPagerState
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.absoluteValue

private const val DEFAULT_FRACTION = 1

@Composable
fun Catalog(
    pane1WidthDp: Dp,
    pane2WidthDp: Dp,
    foldSizeDp: Dp,
    isFeatureHorizontal: Boolean,
    isSinglePortrait: Boolean,
    showTwoPages: Boolean,
    showSmallWindowWidthLayout: Boolean,
    isFoldStateHalfOpened: Boolean,
    catalogList: List<CatalogItem>
) {
    val coroutineScope = rememberCoroutineScope()

    // Calculate page text width based on the smallest pane width
    val pageTextWidth = min(pane1WidthDp, pane2WidthDp)

    // Calculate the necessary page padding, based on pane width differences and fold width
    val pagePadding = abs(pane1WidthDp.value - pane2WidthDp.value).dp + foldSizeDp

    val pagerState = rememberViewPagerState()

    val modifier = Modifier.graphicsLayer {
        val pageOffset =
            pagerState.calculateCurrentOffsetForPage(pagerState.currentPage).absoluteValue
        calculateLinearInterpolation(
            start = if (showTwoPages) 0.90f else 0.80f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }
    }

    val pages = setupPages(
        modifier,
        pageTextWidth,
        pagePadding,
        catalogList,
        isFeatureHorizontal,
        isSinglePortrait,
        showTwoPages,
        showSmallWindowWidthLayout,
        isFoldStateHalfOpened
    ) { destinationPage ->
        coroutineScope.launch {
            pagerState.selectPageNumber(destinationPage)
        }
    }
    PageViews(pagerState, pages, showTwoPages)
}

fun calculateLinearInterpolation(start: Float, stop: Float, fraction: Float): Float {
    return (DEFAULT_FRACTION - fraction) * start + fraction * stop
}

@Composable
fun PageViews(
    pagerState: PagerState = rememberViewPagerState(),
    pages: List<@Composable () -> Unit>,
    showTwoPages: Boolean
) {
    val maxPage = (pages.size - 1).coerceAtLeast(0)

    pagerState.isDualMode = showTwoPages
    pagerState.maxPage = maxPage

    ViewPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) {
        pages[page]()
    }
}

private fun setupPages(
    modifier: Modifier,
    pageTextWidth: Dp,
    pagePadding: Dp = 0.dp,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean,
    isSinglePortrait: Boolean,
    showTwoPages: Boolean,
    showSmallWindowWidthLayout: Boolean,
    isFoldStateHalfOpened: Boolean,
    onItemClick: (Int) -> Unit
): List<@Composable () -> Unit> {
    val pageModifier = if (pageTextWidth.value != 0f && showTwoPages) modifier
        .padding(end = pagePadding)
        .width(pageTextWidth)
        .fillMaxHeight()
        .clipToBounds() else modifier.fillMaxSize()
    return listOf<@Composable () -> Unit>(
        {
            CatalogFirstPage(modifier = pageModifier, catalogList = catalogList, onItemClick)
        },
        {
            CatalogSecondPage(
                modifier = pageModifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal,
                showTwoPages = showTwoPages,
                isSmallWindowWidth = showSmallWindowWidthLayout
            )
        },
        {
            CatalogThirdPage(
                modifier = pageModifier,
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
                modifier = pageModifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal,
                showTwoPages = showTwoPages
            )
        },
        {
            CatalogFifthPage(
                modifier = pageModifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal,
                isSmallWindowWidth = showSmallWindowWidthLayout,
                showTwoPages = showTwoPages
            )
        },
        {
            CatalogSixthPage(
                modifier = pageModifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal
            )
        },
        {
            CatalogSeventhPage(
                modifier = pageModifier,
                catalogList = catalogList,
                isFeatureHorizontal = isFeatureHorizontal,
                isSmallWindowWidth = showSmallWindowWidthLayout,
                showTwoPages = showTwoPages
            )
        },
    )
}
