/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.microsoft.device.samples.dualscreenexperience.R

@Composable
fun PlaceholderOrderHistory() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(0.5f),
            painter = painterResource(R.drawable.empty_boxes),
            contentDescription = null, // null content description indicates that image is decorative
        )
        Spacer(modifier = Modifier.fillMaxHeight(0.1f))
        Text(
            modifier = Modifier.fillMaxWidth(0.75f),
            text = stringResource(R.string.order_history_empty_message),
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground
        )
    }
}
