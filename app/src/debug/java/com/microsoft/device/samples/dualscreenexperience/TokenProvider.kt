/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor() : ITokenProvider {
    override suspend fun getMapToken(): String = BuildConfig.BING_MAPS_KEY
}
