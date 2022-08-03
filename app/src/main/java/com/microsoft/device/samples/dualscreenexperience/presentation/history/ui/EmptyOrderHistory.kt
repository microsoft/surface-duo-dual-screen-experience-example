/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.microsoft.device.dualscreen.windowstate.WindowState
import com.microsoft.device.samples.dualscreenexperience.R

@Composable
fun PlaceholderOrderHistory(showTwoPages: Boolean?, windowState: WindowState?, topBarPaddingDp: Dp, bottomNavPaddingDp: Dp) {
    if (showTwoPages == true) {
        PlaceholderTwoPane(windowState, topBarPaddingDp, bottomNavPaddingDp)
    } else {
        PlaceholderSinglePane()
    }
}

@Composable
fun PlaceholderSinglePane() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlaceholderImage()
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        PlaceholderText()
    }
}

@Composable
fun PlaceholderTwoPane(windowState: WindowState?, topBarPaddingDp: Dp, bottomNavPaddingDp: Dp) {
    if (windowState?.isDualPortrait() == true) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(windowState.pane1SizeDp),
                contentAlignment = Alignment.Center
            ) {
                PlaceholderText()
            }
            Spacer(Modifier.width(windowState.foldSizeDp))
            Box(
                modifier = Modifier.size(windowState.pane2SizeDp),
                contentAlignment = Alignment.Center
            ) {
                PlaceholderImage()
            }
        }
    } else if (windowState?.isDualLandscape() == true) {
        val pane1Size = windowState.pane1SizeDp.copy(height = windowState.pane1SizeDp.height - topBarPaddingDp)
        val pane2Size = windowState.pane2SizeDp.copy(height = windowState.pane2SizeDp.height - bottomNavPaddingDp)

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.size(pane1Size),
                contentAlignment = Alignment.Center
            ) {
                PlaceholderImage()
            }
            Spacer(Modifier.height(windowState.foldSizeDp))
            Box(
                modifier = Modifier.size(pane2Size),
                contentAlignment = Alignment.Center
            ) {
                PlaceholderText(centerText = true)
            }
        }
    }
}

@Composable
fun PlaceholderImage() {
    Image(
        modifier = Modifier.fillMaxWidth(0.5f),
        painter = painterResource(R.drawable.empty_boxes),
        contentDescription = null, // null content description indicates that image is decorative
    )
}

@Composable
fun PlaceholderText(centerText: Boolean = false) {
    Text(
        modifier = Modifier.fillMaxWidth(0.75f),
        text = stringResource(R.string.order_history_empty_message),
        style = MaterialTheme.typography.h3,
        textAlign = if (centerText) TextAlign.Center else TextAlign.Start,
        color = MaterialTheme.colors.onBackground
    )
}
