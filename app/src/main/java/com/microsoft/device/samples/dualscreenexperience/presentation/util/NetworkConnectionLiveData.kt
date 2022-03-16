/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.lifecycle.LiveData

class NetworkConnectionLiveData(private val context: Context?) : LiveData<Boolean>() {

    private var connectivityManager = context?.getSystemService(ConnectivityManager::class.java)

    private var networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()

        updateConnectedValue()

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager?.registerDefaultNetworkCallback(networkCallback)
            else -> context?.registerReceiver(networkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        }
    }

    override fun onInactive() {
        super.onInactive()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        } else {
            context?.unregisterReceiver(networkReceiver)
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnectedValue()
        }
    }

    private fun updateConnectedValue() {
        val isConnected = connectivityManager?.activeNetworkInfo?.isConnected == true
        postValue(isConnected)
    }
}
