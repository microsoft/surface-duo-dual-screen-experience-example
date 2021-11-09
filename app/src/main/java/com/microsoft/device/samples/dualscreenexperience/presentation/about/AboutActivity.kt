/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.SurfaceDuoNavigation
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.dualscreen.utils.wm.isFoldingFeatureVertical
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.ActivityAboutBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.util.RotationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AboutActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: AboutNavigator

    private lateinit var binding: ActivityAboutBinding

    private val rotationViewModel: RotationViewModel by viewModels()

    private lateinit var windowInfoRepository: WindowInfoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeWindowLayoutInfo()
        setupToolbar()
    }

    private fun observeWindowLayoutInfo() {
        windowInfoRepository = windowInfoRepository()
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepository.windowLayoutInfo.collect {
                    onScreenInfoChanged(it)
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onResume() {
        super.onResume()
        SurfaceDuoNavigation.findNavController(this, R.id.about_nav_host_fragment).let {
            navigator.bind(it)
        }
    }

    override fun onPause() {
        super.onPause()
        navigator.unbind()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (navigator.isNavigationAtNotices()) {
            navigator.navigateUp()
        } else {
            finish()
        }
    }

    private fun onScreenInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        rotationViewModel.isDualMode.value = windowLayoutInfo.isInDualMode()
        rotationViewModel.isFoldingFeatureVertical.value = windowLayoutInfo.isFoldingFeatureVertical()
    }
}
