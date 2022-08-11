/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.device.dualscreen.windowstate.WindowState
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductContentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductDrawable
import com.microsoft.device.samples.dualscreenexperience.presentation.util.toDateString

const val NUM_IMAGES_MAX = 3

@Composable
fun OrderHistoryListPage(
    orders: List<Order>?,
    selectedOrder: Order?,
    updateOrder: (Order) -> Unit,
    topBarPadding: Int,
    bottomNavPadding: Int,
    isLandscape: Boolean,
    isSmallWidth: Boolean,
    isDualMode: Boolean?,
    windowState: WindowState?
) {
    // Calculate padding for LazyColumn
    val topBarPaddingDp = with(LocalDensity.current) { topBarPadding.toDp() }
    val bottomNavPaddingDp = with(LocalDensity.current) { bottomNavPadding.toDp() }
    val paddingValues = PaddingValues(bottom = 20.dp + topBarPaddingDp + bottomNavPaddingDp)

    if (orders.isNullOrEmpty())
        PlaceholderOrderHistory(isDualMode, windowState, topBarPaddingDp, bottomNavPaddingDp)
    else
        OrderList(orders, selectedOrder, updateOrder, paddingValues, isLandscape, isSmallWidth)
}

@Composable
fun OrderList(
    orders: List<Order>?,
    selectedOrder: Order?,
    updateOrder: (Order) -> Unit,
    paddingValues: PaddingValues,
    isLandscape: Boolean,
    isSmallWidth: Boolean
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(),
            verticalArrangement = spacedBy(12.dp),
            contentPadding = paddingValues
        ) {
            orders?.map { order ->
                item {
                    OrderCard(order, order == selectedOrder, updateOrder, isLandscape, isSmallWidth)
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    order: Order,
    isSelected: Boolean,
    updateOrder: (Order) -> Unit,
    isLandscape: Boolean,
    isSmallWidth: Boolean
) {
    // Store height of order item text so the order item box is always large enough to contain the text
    var orderItemTextHeight by remember { mutableStateOf(123.dp) }
    val updateTextHeight = { newHeight: Dp ->
        val newHeightWithPadding = newHeight + 32.dp
        if (newHeightWithPadding > orderItemTextHeight)
            orderItemTextHeight = newHeightWithPadding
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        CardBackground(isSelected, { updateOrder(order) }, orderItemTextHeight)
        OrderGraphicAndText(order, isLandscape, isSmallWidth, updateTextHeight)
    }
}

@Composable
fun OrderGraphicAndText(
    order: Order,
    isLandscape: Boolean,
    isSmallWidth: Boolean,
    updateTextHeight: (Dp) -> Unit
) {
    val previewWeight = 1f
    val textWeight = if (isSmallWidth) 1.5f else 2f

    Row(
        modifier = Modifier
            .fillMaxWidth(if (isLandscape) 0.884f else 0.897f)
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = if (isSmallWidth || !isLandscape) spacedBy(40.dp) else spacedBy(88.dp)
    ) {
        OrderGraphic(Modifier.weight(previewWeight), order.items, isSmallWidth)
        OrderText(Modifier.weight(textWeight), order, isSmallWidth, updateTextHeight)
    }
}

@Composable
fun OrderText(
    modifier: Modifier = Modifier,
    order: Order,
    isSmallWidth: Boolean,
    updateTextHeight: (Dp) -> Unit
) {
    val density = LocalDensity.current

    Column(
        modifier = modifier.onSizeChanged {
            val heightDp = with(density) { it.height.toDp() }
            updateTextHeight(heightDp)
        },
        verticalArrangement = spacedBy(12.dp)
    ) {
        OrderDate(orderTimestamp = order.orderTimestamp, isSmallWidth = isSmallWidth)
        OrderId(orderId = order.orderId, isSmallWidth = isSmallWidth)
        OrderAmount(orderPrice = order.totalPrice, isSmallWidth = isSmallWidth)
    }
}

@Composable
fun OrderDate(modifier: Modifier = Modifier, orderTimestamp: Long, isSmallWidth: Boolean) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.order_date, orderTimestamp.toDateString()),
        style = if (isSmallWidth) MaterialTheme.typography.subtitle2 else MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun OrderId(modifier: Modifier = Modifier, orderId: Long?, isSmallWidth: Boolean) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.order_id, orderId ?: ""),
        style = if (isSmallWidth) MaterialTheme.typography.subtitle2 else MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun OrderAmount(modifier: Modifier = Modifier, orderPrice: Int, isSmallWidth: Boolean) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.order_amount, orderPrice),
        style = if (isSmallWidth) MaterialTheme.typography.subtitle2 else MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun CardBackground(isSelected: Boolean, updateOrder: () -> Unit, minHeight: Dp) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .clip(MaterialTheme.shapes.medium)
            .clickable { updateOrder() },
        color = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
    ) {}
}

@Composable
fun OrderGraphic(modifier: Modifier = Modifier, orderItems: MutableList<OrderItem>, isSmallWidth: Boolean) {
    val endIndex = if (orderItems.count() >= NUM_IMAGES_MAX) NUM_IMAGES_MAX else orderItems.count()
    val items = orderItems.subList(0, endIndex)
    val overlap = if (isSmallWidth) 30.dp else 37.dp

    // Calculate guitar image offsets so the order item preview is always centered
    val offsets = when (items.size) {
        2 -> listOf(-overlap / 2, overlap / 2)
        3 -> listOf(-overlap, 0.dp, overlap)
        else -> listOf(0.dp, 0.dp, 0.dp)
    }

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        items.mapIndexed { index, orderItem ->
            OrderItemImage(offsets[index], orderItem)
        }
    }
}

@Composable
fun OrderItemImage(xOffset: Dp, orderItem: OrderItem) {
    Image(
        modifier = Modifier
            .widthIn(min = 50.dp)
            .heightIn(max = 155.dp)
            .offset(x = xOffset),
        painter = painterResource(id = getProductDrawable(orderItem.color, orderItem.bodyShape)),
        contentDescription = stringResource(id = getProductContentDescription(orderItem.color, orderItem.bodyShape))
    )
}
