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
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductContentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductDrawable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val NUM_IMAGES_MAX = 3

@Composable
fun OrderHistoryListPage(orders: List<Order>?, selectedOrder: Order?, updateOrder: (Order) -> Unit) {
    if (orders.isNullOrEmpty())
        PlaceholderOrderHistory()
    else
        OrderList(orders, selectedOrder, updateOrder)
}

@Composable
fun OrderList(orders: List<Order>?, selectedOrder: Order?, updateOrder: (Order) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(),
            verticalArrangement = spacedBy(12.dp),
        ) {
            orders?.map { order ->
                item {
                    OrderListItem(order, order == selectedOrder, updateOrder)
                }
            }
        }
    }
}

@Composable
fun OrderListItem(order: Order, isSelected: Boolean, updateOrder: (Order) -> Unit) {
    Box {
        OrderItemBox(modifier = Modifier.align(Alignment.BottomCenter), isSelected) { updateOrder(order) }
        OrderItemPreviewAndDetails(modifier = Modifier.align(Alignment.BottomCenter), order)
    }
}

@Composable
fun OrderItemPreviewAndDetails(modifier: Modifier = Modifier, order: Order) {
    Row(
        modifier = Modifier
            .padding(20.dp)
            .then(modifier),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = spacedBy(40.dp)
    ) {
        OrderItemPreview(order.items)
        OrderItemText(order)
    }
}

@Composable
fun RowScope.OrderItemText(order: Order) {
    // REVISIT: formatOrderDate extract common code
    Column(
        modifier = Modifier.weight(2f),
        verticalArrangement = spacedBy(12.dp)
    ) {
        OrderDate(orderTimestamp = order.orderTimestamp)
        OrderId(orderId = order.orderId)
        OrderAmount(orderPrice = order.totalPrice)
    }
}

@Composable
fun OrderDate(modifier: Modifier = Modifier, orderTimestamp: Long) {
    Text(
        modifier = modifier,
        text = stringResource(
            R.string.order_date,
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(orderTimestamp))
        ),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun OrderId(modifier: Modifier = Modifier, orderId: Long?) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.order_id, orderId ?: ""),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun OrderAmount(modifier: Modifier = Modifier, orderPrice: Int) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.order_amount, orderPrice),
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun OrderItemBox(modifier: Modifier = Modifier, isSelected: Boolean, updateOrder: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(123.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { updateOrder() }
            .then(modifier),
        color = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface
    ) {}
}

@Composable
fun RowScope.OrderItemPreview(orderItems: MutableList<OrderItem>) {
    val endIndex = if (orderItems.count() >= NUM_IMAGES_MAX) NUM_IMAGES_MAX else orderItems.count()
    val items = orderItems.subList(0, endIndex)
    val alignments = listOf(Alignment.BottomStart, Alignment.BottomCenter, Alignment.BottomEnd)

    Box(modifier = Modifier.weight(1f)) {
        items.mapIndexed { index, orderItem -> PreviewImage(alignments[index], orderItem) }
    }
}

@Composable
fun BoxScope.PreviewImage(alignment: Alignment, orderItem: OrderItem) {
    Image(
        modifier = Modifier
            .widthIn(min = 50.dp)
            .heightIn(max = 155.dp)
            .align(alignment),
        painter = painterResource(id = getProductDrawable(orderItem.color, orderItem.bodyShape)),
        contentDescription = stringResource(id = getProductContentDescription(orderItem.color, orderItem.bodyShape))
    )
}