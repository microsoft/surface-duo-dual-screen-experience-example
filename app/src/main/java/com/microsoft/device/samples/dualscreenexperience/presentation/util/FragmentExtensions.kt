/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

val Fragment.appCompatActivity: AppCompatActivity?
    get() = activity as? AppCompatActivity
