/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.net.Uri

fun getImageUri(imagePath: String?): Uri? = Uri.parse("file:///android_asset/$imagePath")
