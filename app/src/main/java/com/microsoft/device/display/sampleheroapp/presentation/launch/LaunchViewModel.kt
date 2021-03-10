/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.launch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.ItemClickListener

class LaunchViewModel : ViewModel(), ItemClickListener<Boolean> {
    val isDualMode = MutableLiveData(false)
    val isLaunchButtonClicked = MutableLiveData(false)

    override fun onClick(model: Boolean?) {
        isLaunchButtonClicked.value = true
    }
}
