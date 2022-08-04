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
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.contentDescription
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
    isExpanded: Boolean,
    isSmallWidth: Boolean,
    getProductFromOrderItem: (OrderItem) -> Product?,
    addToOrder: (OrderItem) -> Unit,
    isSmallHeight: Boolean
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

    var showDialog by remember { mutableStateOf(false) }
    val updateShowDialog = { newValue: Boolean -> showDialog = newValue }
    var selectedOrderItem: OrderItem? by remember { mutableStateOf(null) }
    val updateOrderItem = { newItem: OrderItem -> selectedOrderItem = newItem }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = columnModifier,
        ) {
            OrderHeader(order, showTwoPages, isSmallWidth)
            Spacer(modifier = Modifier.height(22.dp))
            OrderItems(order.items, paddingValues, isExpanded, isSmallWidth, updateShowDialog, updateOrderItem)
        }
        if (showDialog)
            AddToOrderDialog(
                selectedOrderItem,
                updateShowDialog,
                getProductFromOrderItem,
                addToOrder,
                isSmallHeight
            )
    }
}

@Composable
fun OrderHeader(order: Order, showTwoPages: Boolean, isSmallWidth: Boolean) {
    if (showTwoPages) {
        Text(
            text = stringResource(R.string.toolbar_history_details_title),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(Modifier.height(4.dp))
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        OrderDate(
            modifier = Modifier.weight(1f),
            orderTimestamp = order.orderTimestamp,
            isSmallWidth = isSmallWidth
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = spacedBy(4.dp),
            horizontalAlignment = Alignment.End
        ) {
            OrderId(orderId = order.orderId, isSmallWidth = isSmallWidth)
            OrderAmount(orderPrice = order.totalPrice, isSmallWidth = isSmallWidth)
        }
    }
}

@Composable
fun OrderItems(
    orderItems: MutableList<OrderItem>,
    paddingValues: PaddingValues,
    isExpanded: Boolean,
    isSmallWidth: Boolean,
    updateShowDialog: (Boolean) -> Unit,
    updateOrderItem: (OrderItem) -> Unit
) {
    LazyColumn(
        verticalArrangement = spacedBy(25.dp),
        contentPadding = paddingValues
    ) {
        orderItems.map { orderItem ->
            item {
                OrderItemPreview(orderItem, isExpanded, isSmallWidth, updateShowDialog, updateOrderItem)
            }
        }
    }
}

@Composable
fun OrderItemPreview(
    orderItem: OrderItem,
    isExpanded: Boolean,
    isSmallWidth: Boolean,
    updateShowDialog: (Boolean) -> Unit,
    updateOrderItem: (OrderItem) -> Unit
) {
    var boxWidth by remember { mutableStateOf(100) }
    val updateBoxWidth = { newWidth: Int -> boxWidth = newWidth }

    Box {
        OrderItemDetails(orderItem, isExpanded, isSmallWidth, updateBoxWidth, updateShowDialog, updateOrderItem)
        OrderItemImage(orderItem = orderItem, boxWidthPx = boxWidth)
    }
}

@Composable
fun BoxScope.OrderItemDetails(
    orderItem: OrderItem,
    isExpanded: Boolean,
    isSmallWidth: Boolean,
    updateBoxWidth: (Int) -> Unit,
    updateShowDialog: (Boolean) -> Unit,
    updateOrderItem: (OrderItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .align(Alignment.BottomCenter),
        horizontalArrangement = spacedBy(41.dp)
    ) {
        OrderItemBox(isExpanded, updateBoxWidth)
        OrderItemText(orderItem, isExpanded, isSmallWidth)
        OrderItemViewButton(
            onClick = {
                updateShowDialog(true)
                updateOrderItem(orderItem)
            },
            isSmallWidth = isSmallWidth
        )
    }
}

@Composable
fun RowScope.OrderItemText(orderItem: OrderItem, isExpanded: Boolean, isSmallWidth: Boolean) {
    val rowWeight = if (isExpanded) 7f else 5f

    Column(
        modifier = Modifier
            .weight(rowWeight)
            .fillMaxHeight(),
        verticalArrangement = spacedBy(6.dp)
    ) {
        Text(
            text = orderItem.name,
            style = if (isSmallWidth) MaterialTheme.typography.subtitle2 else MaterialTheme.typography.body2,
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
fun RowScope.OrderItemBox(isExpanded: Boolean, updateBoxWidth: (Int) -> Unit) {
    val rowWeight = if (isExpanded) 2f else 3f

    Surface(
        modifier = Modifier
            .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
            .aspectRatio(1f)
            // .fillMaxHeight()
            .onSizeChanged { updateBoxWidth(it.width) }
            .weight(rowWeight)
            .align(Alignment.Bottom),
        shape = MaterialTheme.shapes.medium
    ) { }
}

@Composable
fun OrderItemImage(orderItem: OrderItem, boxWidthPx: Int) {
    val width = with(LocalDensity.current) { boxWidthPx.toDp() }.value

    Image(
        modifier = Modifier
            .sizeIn(maxHeight = 224.dp, maxWidth = width.dp)
            .heightIn(max = 224.dp)
            .graphicsLayer(
                rotationZ = 30f,
                translationX = boxWidthPx / 2f
            ),
        painter = painterResource(getProductDrawable(orderItem.color, orderItem.bodyShape)),
        contentDescription = stringResource(getProductContentDescription(orderItem.color, orderItem.bodyShape))
    )
}

@Composable
fun RowScope.OrderItemViewButton(onClick: () -> Unit, isSmallWidth: Boolean) {
    val baseTextStyle = MaterialTheme.typography.caption

    TextButton(
        modifier = Modifier
            .weight(3f)
            .align(Alignment.Bottom),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ),
        onClick = { onClick() }
    ) {
        Text(
            text = stringResource(id = R.string.order_history_view),
            style = if (isSmallWidth) baseTextStyle.copy(fontSize = 14.sp) else baseTextStyle,
            fontWeight = FontWeight.Bold
        )
    }
}
