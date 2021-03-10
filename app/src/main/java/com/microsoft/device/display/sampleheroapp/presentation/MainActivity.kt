/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.DuoNavigation
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.dualscreen.bottomnavigation.SurfaceDuoBottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var navigator: AppNavigator
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
    }

    override fun onResume() {
        super.onResume()
        navigator.bind(DuoNavigation.findNavController(this, R.id.nav_host_fragment))

        val bottomNavBar = findViewById<SurfaceDuoBottomNavigationView>(R.id.bottomNavView)
        // bottomNavBar.setupWithNavController(findNavController(this, R.id.nav_host_fragment))
        bottomNavBar.arrangeButtons(3, 0)
    }

    override fun onPause() {
        super.onPause()
        navigator.unbind()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }
}
