/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductContentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductDrawable
import com.microsoft.device.samples.dualscreenexperience.presentation.util.addThousandsSeparator

@Composable
fun OrderHistoryDetailPage(order: Order?, showTwoPages: Boolean?, topBarPadding: Int, bottomNavPadding: Int) {
    if (order == null || showTwoPages == null) {
        // REVISIT
        return
    }

    // Calculate padding for LazyColumn
    val paddingValues = with(LocalDensity.current) {
        PaddingValues(bottom = 20.dp + topBarPadding.toDp() + bottomNavPadding.toDp())
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(),
        ) {
            OrderHeader(order, showTwoPages)
            Spacer(modifier = Modifier.height(22.dp))
            OrderItems(order.items, paddingValues)
        }
    }
}

@Composable
fun OrderHeader(order: Order, showTwoPages: Boolean) {
    if (showTwoPages) {
        Text(
            text = stringResource(R.string.toolbar_history_details_title),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(Modifier.height(4.dp))
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        OrderDate(orderTimestamp = order.orderTimestamp)
        Column(verticalArrangement = spacedBy(4.dp), horizontalAlignment = Alignment.End) {
            OrderId(orderId = order.orderId)
            OrderAmount(orderPrice = order.totalPrice)
        }
    }
}

@Composable
fun OrderItems(orderItems: MutableList<OrderItem>, paddingValues: PaddingValues) {
    LazyColumn(
        verticalArrangement = spacedBy(25.dp),
        contentPadding = paddingValues
    ) {
        orderItems.map { orderItem ->
            item {
                OrderItemPreview(orderItem = orderItem)
            }
        }
    }
}

@Composable
fun OrderItemPreview(orderItem: OrderItem) {
    Box {
        OrderItemDetails(orderItem = orderItem)
        OrderItemImage(orderItem = orderItem)
    }
}

@Composable
fun BoxScope.OrderItemDetails(orderItem: OrderItem) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter),
        horizontalArrangement = spacedBy(41.dp)
    ) {
        OrderItemBox()
        OrderItemText(orderItem = orderItem)
        OrderItemViewButton(orderItem = orderItem)
    }
}

@Composable
fun RowScope.OrderItemText(orderItem: OrderItem) {
    Column(modifier = Modifier.weight(5f), verticalArrangement = spacedBy(6.dp)) {
        Text(
            text = orderItem.name,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            // REVISIT: extract common code from binding adapter formatPrice
            text = "$" + orderItem.price.toFloat().addThousandsSeparator(),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = stringResource(R.string.order_quantity, orderItem.quantity),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun RowScope.OrderItemViewButton(orderItem: OrderItem) {
    TextButton(
        modifier = Modifier
            .weight(2f)
            .align(Alignment.Bottom),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        onClick = { /*TODO*/ }
    ) {
        Text(
            text = stringResource(id = R.string.order_history_view),
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RowScope.OrderItemBox() {
    Box(modifier = Modifier.weight(3f)) {
        Surface(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
                .aspectRatio(1f)
        ) {}
    }
}

@Composable
fun OrderItemImage(orderItem: OrderItem) {
    val translationX = with(LocalDensity.current) { 50.dp.roundToPx().toFloat() }

    Image(
        modifier = Modifier
            .heightIn(max = 224.dp)
            .graphicsLayer(rotationZ = 30f, translationX = translationX),
        painter = painterResource(getProductDrawable(orderItem.color, orderItem.bodyShape)),
        contentDescription = stringResource(
            id = getProductContentDescription(
                orderItem.color,
                orderItem.bodyShape
            )
        )
    )
}
