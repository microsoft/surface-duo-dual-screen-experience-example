/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.content.Context
import android.util.TypedValue
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun Float.dpToPx(context: Context) =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    ).toInt()

fun Float.addThousandsSeparator(): String =
    (NumberFormat.getInstance(Locale.US) as DecimalFormat).apply {
        applyPattern("#,###")
    }.format(this)
