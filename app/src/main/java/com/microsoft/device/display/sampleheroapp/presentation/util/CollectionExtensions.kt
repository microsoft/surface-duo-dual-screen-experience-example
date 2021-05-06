/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

fun <T> Collection<T>?.hasSingleItem(): Boolean = this != null && size == 1

fun <T> Collection<T>?.hasMoreThanOneItem(): Boolean = this != null && size > 1

fun <T> Collection<T>?.sizeOrZero(): Int = this?.size ?: 0

fun <T> Collection<T>?.hasSizeEven(): Boolean = this != null && size % 2 == 0
