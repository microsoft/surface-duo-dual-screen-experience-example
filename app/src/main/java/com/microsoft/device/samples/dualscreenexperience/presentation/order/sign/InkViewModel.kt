/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order.sign

import androidx.lifecycle.ViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DistinctValueLiveData

class InkViewModel : ViewModel() {
    var selectedInkColor = DistinctValueLiveData<Int?>(null)
    var selectedStrokeWidth = DistinctValueLiveData<Float?>(null)
}
