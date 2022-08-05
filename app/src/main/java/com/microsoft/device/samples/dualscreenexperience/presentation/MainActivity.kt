/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.FoldableNavDestination
import androidx.navigation.FoldableNavigation
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.microsoft.device.dualscreen.utils.wm.getFoldingFeature
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.ActivityMainBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeActivity.Companion.EXTRA_ANIMATION_X
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeActivity.Companion.EXTRA_ANIMATION_Y
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeActivity.Companion.EXTRA_APP_SCREEN
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeActivity.Companion.EXTRA_DESIGN_PATTERN
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeActivity.Companion.EXTRA_SDK_COMPONENT
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeActivity.Companion.SHARED_ELEMENT_NAME
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeViewModel.AppScreen
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeViewModel.DesignPattern
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeViewModel.SdkComponent
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.product.ProductViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.store.StoreViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.getTopCenterPoint
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialBalloon
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialBalloonType
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: MainNavigator

    @Inject
    lateinit var tutorial: TutorialBalloon

    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by viewModels()
    private val devViewModel: DevModeViewModel by viewModels()

    private val productViewModel: ProductViewModel by viewModels()
    private val storeViewModel: StoreViewModel by viewModels()

    @VisibleForTesting
    private val orderViewModel: OrderViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private var devModeTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeWindowLayoutInfo()
        setupToolbar()
        setupBottomNavigation()
    }

    private fun observeWindowLayoutInfo() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                WindowInfoTracker.getOrCreate(this@MainActivity)
                    .windowLayoutInfo(this@MainActivity)
                    .collect {
                        onWindowLayoutInfoChanged(it)
                    }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        getMainNavController().let {
            navigator.bind(it)
            it.addOnDestinationChangedListener { _, foldableNavDestination, arguments ->
                showHideBottomNav(arguments?.getBoolean(HIDE_BOTTOM_BAR_KEY, false))

                resetDestinations(foldableNavDestination)
                setupDevModeByDestination(foldableNavDestination)
            }
        }
        binding.bottomNavView.arrangeButtons(BOTTOM_NAV_ITEM_COUNT, 0)
        binding.bottomNavView.allowFlingGesture = false
        binding.bottomNavView.useAnimation = false
    }

    private fun getMainNavController() =
        FoldableNavigation.findNavController(this, R.id.nav_host_fragment)

    private fun showHideBottomNav(shouldHide: Boolean?) {
        binding.bottomNavView.isGone = (shouldHide == true)
    }

    private fun resetDestinations(destination: FoldableNavDestination) {
        when (destination.id) {
            R.id.fragment_store_map -> storeViewModel.reset()
            R.id.fragment_product_list -> productViewModel.reset()
            R.id.fragment_order -> orderViewModel.reset()
        }
    }

    override fun onPause() {
        super.onPause()
        navigator.unbind()
        tutorial.hide()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupBottomNavigation() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_stores_graph -> {
                    navigator.navigateToStores()
                    hideStoresTutorial()
                }
                R.id.navigation_catalog_graph -> {
                    navigator.navigateToCatalog()
                    hideStoresTutorial()
                }
                R.id.navigation_products_graph -> {
                    navigator.navigateToProducts()
                    hideStoresTutorial()
                }
                R.id.navigation_orders_graph -> {
                    navigator.navigateToOrders()
                }
            }
            true
        }

        binding.bottomNavView.setOnItemReselectedListener {
            // do nothing to prevent reset to start destination
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (navigator.isNavigationAtStart()) {
            finish()
        }
        super.onBackPressed()
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        if (windowLayoutInfo.isInDualMode() != layoutInfoViewModel.isDualMode.value) {
            tutorial.hide()
            invalidateOptionsMenu()
            Log.i("HISTORY_TEST", "spanned")
        }
        layoutInfoViewModel.isDualMode.value = windowLayoutInfo.isInDualMode()
        layoutInfoViewModel.foldingFeature.value = windowLayoutInfo.getFoldingFeature()
        if (windowLayoutInfo.isInDualMode()) {
            tutorialViewModel.onDualMode()
        }
    }

    private fun setupTutorialObserver() {
        tutorialViewModel.showStoresTutorial.observe(this) { isTutorialTriggered ->
            if (isTutorialTriggered == true && tutorialViewModel.shouldShowStoresTutorial()) {
                showStoresTutorial()
            }
        }
    }

    private fun showStoresTutorial() {
        val storeItem = findViewById<BottomNavigationItemView>(R.id.navigation_stores_graph)
        if (!isFinishing) {
            tutorial.show(storeItem, TutorialBalloonType.STORES)
        }
    }

    private fun hideStoresTutorial() {
        tutorialViewModel.onStoresOpen()
        if (tutorial.currentBalloonType == TutorialBalloonType.STORES) {
            tutorial.hide()
        }
    }

    private fun showDeveloperModeTutorial(anchorView: View) {
        if (tutorialViewModel.shouldShowDeveloperModeTutorial()) {
            tutorial.hide()
            if (!isFinishing) {
                tutorial.show(anchorView, TutorialBalloonType.DEVELOPER_MODE)
            }
        } else {
            setupTutorialObserver()
        }
    }

    private fun setupDevModeByDestination(destination: FoldableNavDestination) {
        when (destination.id) {
            R.id.fragment_store_map ->
                setupDevMode(AppScreen.STORES_MAP, DesignPattern.EXTENDED_CANVAS, SdkComponent.BOTTOM_NAVIGATION_VIEW)
            R.id.fragment_store_list ->
                setupDevMode(AppScreen.STORES_LIST, DesignPattern.DUAL_VIEW, SdkComponent.NAVIGATION)
            R.id.fragment_store_details ->
                setupDevMode(AppScreen.STORES_DETAILS, DesignPattern.LIST_DETAIL, SdkComponent.NAVIGATION)
            R.id.fragment_catalog ->
                setupDevMode(AppScreen.CATALOG, DesignPattern.TWO_PAGE, SdkComponent.BOTTOM_NAVIGATION_VIEW)
            R.id.fragment_product_list ->
                setupDevMode(AppScreen.PRODUCTS_LIST_DETAILS, DesignPattern.LIST_DETAIL, SdkComponent.BOTTOM_NAVIGATION_VIEW)
            R.id.fragment_product_details ->
                setupDevMode(AppScreen.PRODUCTS_LIST_DETAILS, DesignPattern.LIST_DETAIL, SdkComponent.BOTTOM_NAVIGATION_VIEW)
            R.id.fragment_product_customize ->
                setupDevMode(AppScreen.PRODUCTS_CUSTOMIZE, DesignPattern.COMPANION_PANE, SdkComponent.NAVIGATION)
            R.id.fragment_order ->
                setupDevMode(AppScreen.ORDER, DesignPattern.NONE, SdkComponent.RECYCLER_VIEW)
            R.id.fragment_order_receipt ->
                setupDevMode(AppScreen.ORDER, DesignPattern.NONE, SdkComponent.RECYCLER_VIEW)
        }
    }

    private fun setupDevMode(appScreen: AppScreen, designPattern: DesignPattern, sdkComponent: SdkComponent) {
        devViewModel.appScreen = appScreen
        devViewModel.designPattern = designPattern
        devViewModel.sdkComponent = sdkComponent

        if (designPattern != DesignPattern.NONE) {
            devModeTextView?.text = getString(R.string.toolbar_dev_mode_design_pattern, getString(designPattern.stringResId))
        } else {
            devModeTextView?.text = getString(R.string.toolbar_dev_mode)
        }
        devModeTextView?.let {
            showDeveloperModeTutorial(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (layoutInfoViewModel.isDualMode.value == true) {
            menuInflater.inflate(R.menu.main_menu, menu)
            menu.findItem(R.id.menu_main_dev_mode)?.actionView?.apply {
                setOnClickListener {
                    onDevModeClicked(it)
                }
                devModeTextView = findViewById(R.id.dev_mode_label)
                getMainNavController().currentDestination?.let {
                    setupDevModeByDestination(it)
                }
            }
        } else {
            menuInflater.inflate(R.menu.main_single_menu, menu)
            setupTutorialObserver()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_main_about) {
            onAboutClicked()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onAboutClicked() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun onDevModeClicked(view: View) {
        val options = makeSceneTransitionAnimation(this, view, SHARED_ELEMENT_NAME)

        Intent(this, DevModeActivity::class.java).apply {
            val viewCenter = view.getTopCenterPoint()

            putExtra(EXTRA_ANIMATION_X, viewCenter.x)
            putExtra(EXTRA_ANIMATION_Y, viewCenter.y)
            putExtra(EXTRA_APP_SCREEN, devViewModel.appScreen.path)
            putExtra(EXTRA_DESIGN_PATTERN, devViewModel.designPattern.path)
            putExtra(EXTRA_SDK_COMPONENT, devViewModel.sdkComponent.path)

            startActivity(this, options.toBundle())
        }
        tutorialViewModel.onDeveloperModeOpen()
    }

    override fun onDestroy() {
        super.onDestroy()
        devModeTextView = null
    }

    @VisibleForTesting
    fun getItemListLiveData() = orderViewModel.itemList

    @VisibleForTesting
    fun getSubmittedOrderLiveData() = orderViewModel.submittedOrder

    companion object {
        const val HIDE_BOTTOM_BAR_KEY = "hideBottomNav"
        const val BOTTOM_NAV_ITEM_COUNT = 4
    }
}
