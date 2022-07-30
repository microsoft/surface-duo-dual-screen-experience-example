/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.contentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.StarRatingView
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductDrawable
import com.microsoft.device.samples.dualscreenexperience.presentation.util.toPriceString

@Composable
fun AddToOrderDialog(
    selectedOrderItem: OrderItem?,
    updateShowDialog: (Boolean) -> Unit,
    getProductFromOrderItem: (OrderItem) -> Product?,
    addToOrder: (OrderItem) -> Unit
) {
    if (selectedOrderItem == null) {
        updateShowDialog(false)
        return
    }

    val bottomRoundShape = MaterialTheme.shapes.medium.copy(
        topEnd = CornerSize(0.dp), topStart = CornerSize(0.dp)
    )

    Dialog(onDismissRequest = { updateShowDialog(false) }) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.secondary, MaterialTheme.shapes.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DialogOrderPreview(orderItem = selectedOrderItem)
            Column(
                modifier = Modifier
                    .widthIn(max = 303.dp)
                    .background(MaterialTheme.colors.surface, bottomRoundShape)
                    .padding(32.dp),
                verticalArrangement = spacedBy(8.dp)
            ) {
                DialogOrderTitle(orderItem = selectedOrderItem)
                DialogOrderRating(orderItem = selectedOrderItem, getProductFromOrderItem = getProductFromOrderItem)
                DialogOrderPrice(orderItem = selectedOrderItem)
                DialogOrderDescription(orderItem = selectedOrderItem, getProductFromOrderItem = getProductFromOrderItem)
                Spacer(Modifier.height(14.dp))
                DialogButtons(orderItem = selectedOrderItem, addToOrder = addToOrder, updateShowDialog)
            }
        }
    }
}

@Composable
fun DialogOrderPreview(orderItem: OrderItem) {
    val resId = getProductDrawable(orderItem.color, orderItem.bodyShape)
    val vector = ImageVector.vectorResource(id = resId)
    val painter = rememberVectorPainter(image = vector)

    Box(
        modifier = Modifier.padding(vertical = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(height = 65.dp, width = 239.dp)
        ) {
            // Scale drawable to fit in canvas - since the drawable will be rotated 90 deg, the
            // desired height of the drawable will actually be the canvas width
            val desiredHeight = size.width
            val desiredWidth = desiredHeight * painter.intrinsicSize.width / painter.intrinsicSize.height

            // Translate drawable to center of canvas before rotation
            val translateX = (size.width - desiredWidth) / 2
            val translateY = (desiredHeight - size.height) / 2

            withTransform(
                {
                    rotate(90F)
                    translate(left = translateX, top = -translateY)
                }
            ) {
                with(painter) {
                    draw(Size(width = desiredWidth, height = desiredHeight))
                }
            }
        }
    }
}

@Composable
fun DialogOrderTitle(orderItem: OrderItem) {
    Text(
        text = orderItem.name,
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun DialogOrderRating(orderItem: OrderItem, getProductFromOrderItem: (OrderItem) -> Product?) {
    AndroidView(
        factory = {
            StarRatingView(it).apply {
                val product = getProductFromOrderItem(orderItem)
                product?.let { setValue(product.rating) }
            }
        }
    )
}

@Composable
fun DialogOrderPrice(orderItem: OrderItem) {
    Text(
        text = orderItem.price.toFloat().toPriceString(),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun DialogOrderDescription(orderItem: OrderItem, getProductFromOrderItem: (OrderItem) -> Product?) {
    Text(
        text = getProductFromOrderItem(orderItem)?.description ?: "",
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun ColumnScope.DialogButtons(
    orderItem: OrderItem,
    addToOrder: (OrderItem) -> Unit,
    updateShowDialog: (Boolean) -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.add_to_order_animation))
    var isPlaying by remember { mutableStateOf(false) }
    val progress by animateLottieCompositionAsState(composition, isPlaying = isPlaying)

    val buttonShape = RoundedCornerShape(100.dp)

    Row(
        modifier = Modifier
            .align(Alignment.End)
            .height(IntrinsicSize.Max),
        horizontalArrangement = spacedBy(8.dp)
    ) {
        TextButton(
            onClick = { updateShowDialog(false) },
            shape = buttonShape,
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary.copy(alpha = .08f),
                contentColor = MaterialTheme.colors.onBackground
            )
        ) {
            Text(
                text = stringResource(R.string.order_sign_cancel),
                style = MaterialTheme.typography.caption.copy(fontSize = 14.sp)
            )
        }
        LottieAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .clip(buttonShape)
                .pointerInput(orderItem) {
                    detectTapGestures {
                        // Start animating if not already in progress
                        if (progress == 0f) {
                            isPlaying = true
                            addToOrder(orderItem)
                        }
                    }
                }
                .contentDescription(stringResource(R.string.product_accessibility_add_to_order)),
            composition = composition,
            progress = { progress },
            maintainOriginalImageBounds = true,
            contentScale = ContentScale.FillWidth
        )
    }

    // Close dialog when animation is complete
    if (isPlaying && progress == 1f) {
        updateShowDialog(false)
        isPlaying = false
    }
}
