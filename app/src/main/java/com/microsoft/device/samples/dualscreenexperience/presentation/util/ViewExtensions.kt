/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

fun View.getTopCenterPoint(): Point {
    val point = IntArray(2)
    getLocationOnScreen(point)
    point[0] += width / 2
    return Point(point[0], point[1])
}

fun RecyclerView.addOrReplaceItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
    if (itemDecorationCount == 1) {
        removeItemDecorationAt(0)
    }
    addItemDecoration(itemDecoration)
}

fun Context.readTextFromAsset(fileName: String): String =
    assets.open(fileName).bufferedReader().use { it.readText() }

fun TextView.addClickableLink(textToLink: String, onClickListener: ClickableSpan) {
    val spannableString = SpannableStringBuilder(text)
    val clickableIndex = text.indexOf(textToLink)
    spannableString.setSpan(onClickListener, clickableIndex, clickableIndex + textToLink.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    text = spannableString
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = Color.TRANSPARENT
}
