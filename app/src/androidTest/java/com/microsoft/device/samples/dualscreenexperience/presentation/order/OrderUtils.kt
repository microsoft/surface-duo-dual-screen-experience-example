/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_DETAILS
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_DETAILS_SUBMITTED
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.RECOMMENDATIONS_SIZE_ONE
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.RECOMMENDATIONS_SIZE_THREE
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.RECOMMENDATIONS_SIZE_TWO
import com.microsoft.device.samples.dualscreenexperience.presentation.product.clickOnCustomizeButton
import com.microsoft.device.samples.dualscreenexperience.presentation.product.clickOnListItemAtPosition
import com.microsoft.device.samples.dualscreenexperience.presentation.product.navigateToProductsSection
import com.microsoft.device.samples.dualscreenexperience.presentation.product.selectColor
import com.microsoft.device.samples.dualscreenexperience.presentation.product.selectShape
import com.microsoft.device.samples.dualscreenexperience.util.atRecyclerAdapterPosition
import com.microsoft.device.samples.dualscreenexperience.util.clickChildViewWithId
import com.microsoft.device.samples.dualscreenexperience.util.forceClick
import com.microsoft.device.samples.dualscreenexperience.util.scrollRecyclerViewTo
import com.microsoft.device.samples.dualscreenexperience.util.scrollRecyclerViewToEnd
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf

fun navigateToOrdersSection() {
    onView(withId(R.id.navigation_orders_graph)).perform(forceClick())
}

fun checkEmptyPage() {
    onView(withId(R.id.order_items)).perform(scrollRecyclerViewTo(R.id.order_empty_container))
    onView(withId(R.id.order_empty_image)).check(matches(isDisplayed()))

    onView(withId(R.id.order_items)).perform(scrollRecyclerViewTo(R.id.order_empty_container))
    onView(withId(R.id.order_empty_message)).check(matches(isDisplayed()))
}

fun checkOrderRecommendationsPage(size: Int, recommendationsPosition: Int) {
    checkRecommendationsPage(size, recommendationsPosition, withId(R.id.order_items))
}

fun checkOrderReceiptRecommendationsPage(size: Int, recommendationsPosition: Int) {
    checkRecommendationsPage(size, recommendationsPosition, withId(R.id.order_receipt_items))
}

fun checkRecommendationsPage(size: Int, recommendationsPosition: Int, parentMatcher: Matcher<View>) {
    onView(parentMatcher).check(
        matches(
            atRecyclerAdapterPosition(
                recommendationsPosition,
                R.id.order_recommendations_title,
                isDisplayed()
            )
        )
    )
    when (size) {
        RECOMMENDATIONS_SIZE_ONE ->
            onView(parentMatcher).check(
                matches(
                    atRecyclerAdapterPosition(
                        recommendationsPosition,
                        R.id.order_recommendations_item_first,
                        getRecommendationItemMatcher()
                    )
                )
            )
        RECOMMENDATIONS_SIZE_TWO -> {
            onView(parentMatcher).check(
                matches(
                    atRecyclerAdapterPosition(
                        recommendationsPosition,
                        R.id.order_recommendations_item_first,
                        getRecommendationItemMatcher()
                    )
                )
            )
            onView(parentMatcher).check(
                matches(
                    atRecyclerAdapterPosition(
                        recommendationsPosition,
                        R.id.order_recommendations_item_second,
                        getRecommendationItemMatcher()
                    )
                )
            )
        }
        RECOMMENDATIONS_SIZE_THREE -> {
            onView(parentMatcher).check(
                matches(
                    atRecyclerAdapterPosition(
                        recommendationsPosition,
                        R.id.order_recommendations_item_first,
                        getRecommendationItemMatcher()
                    )
                )
            )
            onView(parentMatcher).check(
                matches(
                    atRecyclerAdapterPosition(
                        recommendationsPosition,
                        R.id.order_recommendations_item_second,
                        getRecommendationItemMatcher()
                    )
                )
            )
            onView(parentMatcher).check(
                matches(
                    atRecyclerAdapterPosition(
                        recommendationsPosition,
                        R.id.order_recommendations_item_third,
                        getRecommendationItemMatcher()
                    )
                )
            )
        }
    }
}

fun getRecommendationItemMatcher(): Matcher<View?> =
    allOf(
        isDisplayed(),
        hasDescendant(
            allOf(
                withId(R.id.product_name),
                isDisplayed()
            )
        ),
        hasDescendant(
            allOf(
                withId(R.id.product_rating),
                isDisplayed()
            )
        ),
        hasDescendant(
            allOf(
                withId(R.id.product_image),
                isDisplayed()
            )
        ),
        hasDescendant(
            allOf(
                withId(R.id.product_add_button),
                isDisplayed()
            )
        )
    )

fun clickOnAddFirstRecommendationItem() {
    onView(
        allOf(
            withId(R.id.product_add_button),
            isDescendantOfA(withId(R.id.order_recommendations_item_first))
        )
    ).perform(forceClick())
}

fun clickOnPlaceOrderButton() {
    onView(withId(R.id.product_details_customize_place_order)).perform(forceClick())
}

fun addProductToOrder(itemPosition: Int = 0, bodyShape: ProductType?, color: ProductColor?) {
    navigateToProductsSection()
    clickOnListItemAtPosition(itemPosition)

    clickOnCustomizeButton()
    selectShape(bodyShape)
    selectColor(color)

    clickOnPlaceOrderButton()
}

fun checkOrderDetails() {
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS,
                R.id.total_title,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS,
                R.id.total_price,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS,
                R.id.submit_button,
                isDisplayed()
            )
        )
    )
}

fun checkOrderItemList(position: Int) {
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_name,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_price,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_image,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_remove,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_quantity_plus,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_quantity_minus,
                isDisplayed()
            )
        )
    )
}

fun checkOrderReceiptItems(position: Int) {
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_name,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_price,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_image,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_remove,
                not(isDisplayed())
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_quantity_plus,
                not(isDisplayed())
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_quantity_minus,
                not(isDisplayed())
            )
        )
    )
}

fun scrollOrderToEnd() {
    onView(withId(R.id.order_items)).perform(scrollRecyclerViewToEnd())
}

fun scrollOrderReceiptToEnd() {
    onView(withId(R.id.order_receipt_items)).perform(scrollRecyclerViewToEnd())
}

fun clickOnItemRemove(position: Int) {
    onView(withId(R.id.order_items)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            clickChildViewWithId(R.id.product_remove)
        )
    )
}

fun clickOnItemQuantityPlus(position: Int) {
    onView(withId(R.id.order_items)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            clickChildViewWithId(R.id.product_quantity_plus)
        )
    )
}

fun clickOnItemQuantityMinus(position: Int) {
    onView(withId(R.id.order_items)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            clickChildViewWithId(R.id.product_quantity_minus)
        )
    )
}

fun checkItemQuantity(position: Int, quantity: Int) {
    onView(withId(R.id.order_items)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.product_quantity,
                allOf(
                    isDisplayed(),
                    withText(containsString(quantity.toString()))
                )
            )
        )
    )
}

fun clickOnSubmitOrderButton() {
    onView(withId(R.id.order_items)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            POSITION_DETAILS,
            clickChildViewWithId(R.id.submit_button)
        )
    )
}

fun checkOrderSubmittedDetails() {
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS_SUBMITTED,
                R.id.total_title,
                not(isDisplayed())
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS_SUBMITTED,
                R.id.total_price,
                not(isDisplayed())
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS_SUBMITTED,
                R.id.submit_button,
                not(isDisplayed())
            )
        )
    )

    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS_SUBMITTED,
                R.id.order_date,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS_SUBMITTED,
                R.id.order_id,
                isDisplayed()
            )
        )
    )
    onView(withId(R.id.order_receipt_items)).check(
        matches(
            atRecyclerAdapterPosition(
                POSITION_DETAILS_SUBMITTED,
                R.id.order_amount,
                isDisplayed()
            )
        )
    )
}

const val SINGLE_MODE_ORDER_ITEM_POS = 1
const val DUAL_PORTRAIT_ORDER_ITEM_POS = 2
const val DUAL_LANDSCAPE_ORDER_ITEM_POS = 1
