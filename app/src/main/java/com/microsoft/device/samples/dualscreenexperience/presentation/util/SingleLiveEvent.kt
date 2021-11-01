/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.microsoft.device.samples.dualscreenexperience.BuildConfig
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */
class SingleLiveEvent<T>(initValue: T) : MutableLiveData<T>(initValue) {
    private val pendingValue = AtomicBoolean(false)

    @MainThread
    override fun observe(holder: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers() && BuildConfig.DEBUG) {
            Log.w(
                SINGLE_LIVE_EVENT_TAG,
                "Multiple observers registered but only one will be notified of changes."
            )
        }

        // Observe the internal MutableLiveData
        super.observe(
            holder,
            { t ->
                if (pendingValue.compareAndSet(true, false)) {
                    observer.onChanged(t)
                }
            }
        )
    }

    @MainThread
    override fun setValue(t: T?) {
        pendingValue.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        const val SINGLE_LIVE_EVENT_TAG = "SingleLiveEvent"
    }
}
