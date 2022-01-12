/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData

/**
 * A MutableLiveData that only calls setValue() if the value is different from the previous one.
 */
class DistinctValueLiveData<T>(initValue: T) : MutableLiveData<T>(initValue) {

    @MainThread
    override fun setValue(newValue: T?) {
        if (newValue != value) {
            super.setValue(newValue)
        }
    }

    fun isNotInitialized() = (value == null)
}
