/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.GuitarType
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType
import com.microsoft.device.samples.dualscreenexperience.util.atRecyclerAdapterPosition
import com.microsoft.device.samples.dualscreenexperience.util.clickChildViewWithId
import com.microsoft.device.samples.dualscreenexperience.util.forceClick
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNot.not

fun navigateToProductsSection() {
    onView(withId(R.id.navigation_products_graph)).perform(forceClick())
}

fun checkProductList(position: Int, product: Product) {
    onView(withId(R.id.product_list)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_name,
                withText(product.name)
            )
        )
    )
    onView(withId(R.id.product_list)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.star_rating_text,
                withText(product.rating.toString())
            )
        )
    )
    onView(withId(R.id.product_list)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_description,
                withText(product.description)
            )
        )
    )
}

fun checkProductDetails(product: Product) {
    onView(withId(R.id.product_details_name)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(product.name)
            )
        )
    )
    onView(withId(R.id.product_details_rating)).check(
        matches(
            allOf(
                isDisplayed(),
                hasDescendant(withText(product.rating.toString()))
            )
        )
    )
    onView(withId(R.id.product_details_pickup)).check(matches(isDisplayed()))
    onView(withId(R.id.product_details_frets)).check(matches(isDisplayed()))
    onView(withId(R.id.product_details_type_title)).check(matches(isDisplayed()))
    onView(withId(R.id.product_details_type_description)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(containsString(product.name))
            )
        )
    )
}

fun checkCustomizeButton() {
    onView(withId(R.id.product_details_customize_button)).check(matches(isDisplayed()))
}

fun clickOnListItemAtPosition(position: Int) {
    onView(withId(R.id.product_list)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            clickChildViewWithId(R.id.product_item)
        )
    )
}

fun clickOnCustomizeButton() {
    onView(withId(R.id.product_details_customize_button)).perform(forceClick())
}

fun checkCustomizeControl() {
    onView(withId(R.id.product_customize_color_title)).check(matches(isDisplayed()))
    onView(withId(R.id.product_customize_color_container)).check(matches(isDisplayed()))
    onView(withId(R.id.product_customize_body_title)).check(matches(isDisplayed()))
    onView(withId(R.id.product_customize_body_container)).check(matches(isDisplayed()))
}

fun checkCustomizeShapes() {
    onView(withId(R.id.product_customize_body_1)).check(matches(isDisplayed()))
    onView(withId(R.id.product_customize_body_2)).check(matches(isDisplayed()))
    onView(withId(R.id.product_customize_body_3)).check(matches(isDisplayed()))
    onView(withId(R.id.product_customize_body_4)).check(matches(isDisplayed()))
}

fun checkCustomizeImagePortrait() {
    onView(withId(R.id.product_customize_image)).check(matches(isDisplayed()))
}

fun checkCustomizeImageLandscape() {
    onView(withId(R.id.product_customize_image_landscape)).check(matches(isDisplayed()))
}

fun checkCustomizeDetails(product: Product) {
    checkProductDetails(product)
    checkPlaceOrderButton()
}

fun checkSingleModePlaceOrderButton() {
    onView(withId(R.id.product_customize_place_order_button)).check(matches(isDisplayed()))
}

fun checkPlaceOrderButton() {
    onView(withId(R.id.product_details_customize_place_order)).check(matches(isDisplayed()))
}

fun checkCustomizeDetailsImagePortrait() {
    onView(withId(R.id.product_details_image)).check(matches(isDisplayed()))
}

fun checkCustomizeDetailsImageLandscape() {
    onView(withId(R.id.product_details_image)).check(matches(not(isDisplayed())))
}

fun checkShapeSelected(shape: ProductType?) {
    onView(withContentDescription(shape?.toString())).check(matches(isSelected()))
}

fun selectShape(shape: ProductType?) {
    onView(withContentDescription(shape?.toString())).perform(forceClick())
}

fun checkColorSelected(color: ProductColor?) {
    onView(withContentDescription(color?.toString())).check(matches(isSelected()))
}

fun selectColor(color: ProductColor?) {
    onView(withContentDescription(color?.toString())).perform(forceClick())
}

fun checkGuitarTypeSelected(guitarType: GuitarType?) {
    getGuitarTypeViewId(guitarType)?.let {
        onView(withId(it)).check(matches(isChecked()))
    }
}

fun selectGuitarType(guitarType: GuitarType?) {
    getGuitarTypeViewId(guitarType)?.let {
        onView(withId(it)).perform(forceClick())
    }
}

fun getGuitarTypeViewId(guitarType: GuitarType?) =
    when (guitarType) {
        GuitarType.BASS -> R.id.product_customize_type_bass
        GuitarType.NORMAL -> R.id.product_customize_type_normal
        else -> null
    }

fun checkCustomizeImagePortraitContent(
    color: ProductColor?,
    shape: ProductType?,
    guitarType: GuitarType? = GuitarType.BASS
) {
    checkCustomizeImageContent(withId(R.id.product_customize_image), color, shape, guitarType)
}

fun checkCustomizeImageLandscapeContent(
    color: ProductColor?,
    shape: ProductType?,
    guitarType: GuitarType? = GuitarType.BASS
) {
    checkCustomizeImageContent(withId(R.id.product_customize_image_landscape), color, shape, guitarType)
}

fun checkCustomizeDetailsImageContent(
    color: ProductColor?,
    shape: ProductType?,
    guitarType: GuitarType? = GuitarType.BASS
) {
    checkCustomizeImageContent(withId(R.id.product_details_image), color, shape, guitarType)
}

fun checkCustomizeImageContent(
    parentMatcher: Matcher<View>,
    color: ProductColor?,
    shape: ProductType?,
    guitarType: GuitarType?
) {
    onView(parentMatcher).check(
        matches(
            allOf(
                isDisplayed(),
                withContentDescription(containsString(shape?.toString()?.replace('_', ' ')?.lowercase())),
                withContentDescription(containsString(color?.toString()?.replace('_', ' ')?.lowercase())),
                withContentDescription(containsString(guitarType?.toString()?.lowercase()))
            )
        )
    )
}

fun goBack() {
    Espresso.pressBack()
}

val product = Product(
    1,
    "EG - 29387 Wood",
    6495,
    "Wood body with gloss finish, Three Player Series pickups, 9.5\"-radius fingerboard, 2-point tremolo bridge",
    3.1f,
    21,
    5,
    ProductType.CLASSIC,
    ProductColor.ORANGE,
    GuitarType.BASS
)

const val PRODUCT_FIRST_POSITION = 0
