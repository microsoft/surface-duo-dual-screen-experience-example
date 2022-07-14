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
import androidx.compose.foundation.layout.padding
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
import com.microsoft.device.samples.dualscreenexperience.presentation.util.toPriceString

@Composable
fun OrderHistoryDetailPage(
    order: Order?,
    showTwoPages: Boolean?,
    topBarPadding: Int,
    bottomNavPadding: Int,
    isLandscape: Boolean,
    isExpanded: Boolean
) {
    if (order == null || showTwoPages == null) {
        return
    }

    // Calculate padding for LazyColumn
    val paddingValues = with(LocalDensity.current) {
        PaddingValues(bottom = 20.dp + topBarPadding.toDp() + bottomNavPadding.toDp())
    }

    val columnModifier = if (isLandscape)
        Modifier
            .fillMaxWidth(0.915f)
            .padding(top = 32.dp)
            .fillMaxHeight()
    else
        Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = columnModifier,
        ) {
            OrderHeader(order, showTwoPages)
            Spacer(modifier = Modifier.height(22.dp))
            OrderItems(order.items, paddingValues, isExpanded)
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
fun OrderItems(orderItems: MutableList<OrderItem>, paddingValues: PaddingValues, isExpanded: Boolean) {
    LazyColumn(
        verticalArrangement = spacedBy(25.dp),
        contentPadding = paddingValues
    ) {
        orderItems.map { orderItem ->
            item {
                OrderItemPreview(orderItem = orderItem, isExpanded)
            }
        }
    }
}

@Composable
fun OrderItemPreview(orderItem: OrderItem, isExpanded: Boolean) {
    Box {
        OrderItemDetails(orderItem = orderItem, isExpanded)
        OrderItemImage(orderItem = orderItem)
    }
}

@Composable
fun BoxScope.OrderItemDetails(orderItem: OrderItem, isExpanded: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter),
        horizontalArrangement = spacedBy(41.dp)
    ) {
        OrderItemBox(isExpanded)
        OrderItemText(orderItem = orderItem, isExpanded)
        OrderItemViewButton(orderItem = orderItem, isExpanded)
    }
}

@Composable
fun RowScope.OrderItemText(orderItem: OrderItem, isExpanded: Boolean) {
    val rowWeight = if (isExpanded) 7f else 5f

    Column(modifier = Modifier.weight(rowWeight), verticalArrangement = spacedBy(6.dp)) {
        Text(
            text = orderItem.name,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            text = orderItem.price.toFloat().toPriceString(),
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
fun RowScope.OrderItemViewButton(orderItem: OrderItem, isExpanded: Boolean) {
    val rowWeight = if (isExpanded) 3f else 2f

    TextButton(
        modifier = Modifier
            .weight(rowWeight)
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
fun RowScope.OrderItemBox(isExpanded: Boolean) {
    val rowWeight = if (isExpanded) 2f else 3f

    Box(modifier = Modifier.weight(rowWeight)) {
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
