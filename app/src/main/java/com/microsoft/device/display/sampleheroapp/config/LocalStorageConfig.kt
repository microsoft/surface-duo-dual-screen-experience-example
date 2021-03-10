/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.config

import com.microsoft.device.display.sampleheroapp.BuildConfig

object LocalStorageConfig {
    const val DB_NAME = "dealers_delight_db"
    const val DB_ASSET_PATH = "database/$DB_NAME"

    const val PREF_NAME = BuildConfig.APPLICATION_ID
    const val PREF_NAME_TEST = BuildConfig.APPLICATION_ID + ".test"
    enum class PrefTutorialKey { LAUNCH }
}
