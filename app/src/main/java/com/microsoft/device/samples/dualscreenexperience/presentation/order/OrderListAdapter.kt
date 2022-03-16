/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.samples.dualscreenexperience.databinding.ItemOrderBinding
import com.microsoft.device.samples.dualscreenexperience.databinding.ItemOrderDetailsBinding
import com.microsoft.device.samples.dualscreenexperience.databinding.ItemOrderDetailsSubmittedBinding
import com.microsoft.device.samples.dualscreenexperience.databinding.ItemProductRecommendationsBinding
import com.microsoft.device.samples.dualscreenexperience.databinding.OrderEmptyBinding
import com.microsoft.device.samples.dualscreenexperience.databinding.OrderRecommendationsBinding
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductContentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductDrawable
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListHandler
import com.microsoft.device.samples.dualscreenexperience.presentation.util.ItemClickListener
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.QuantityDataListHandler
import com.microsoft.device.samples.dualscreenexperience.presentation.util.hasMoreThanOneItem
import com.microsoft.device.samples.dualscreenexperience.presentation.util.hasSingleItem
import com.microsoft.device.samples.dualscreenexperience.presentation.util.rotate
import com.microsoft.device.samples.dualscreenexperience.presentation.util.sizeOrZero

class OrderListAdapter(
    context: Context,
    private val orderClickListener: ItemClickListener<Boolean>? = null,
    private val submittedOrder: Order? = null,
    private val quantityDataListHandler: QuantityDataListHandler<OrderItem>,
    private val recommendationsHandler: DataListHandler<Product>,
    var isDualMode: Boolean = false,
    var isDualPortrait: Boolean = false,
    private val isEditEnabled: Boolean = true
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            TYPE_EMPTY ->
                OrderEmptyViewHolder(
                    OrderEmptyBinding.inflate(layoutInflater, parent, false)
                )
            TYPE_RECOMMENDATIONS ->
                OrderRecommendationsViewHolder(
                    OrderRecommendationsBinding.inflate(layoutInflater, parent, false),
                    recommendationsHandler
                )
            TYPE_DETAILS, TYPE_DETAILS_SPACE ->
                OrderDetailsViewHolder(
                    ItemOrderDetailsBinding.inflate(layoutInflater, parent, false),
                    orderClickListener
                )
            TYPE_DETAILS_SUBMITTED ->
                OrderDetailsSubmittedViewHolder(
                    ItemOrderDetailsSubmittedBinding.inflate(layoutInflater, parent, false),
                    submittedOrder
                )
            else ->
                OrderItemViewHolder(
                    ItemOrderBinding.inflate(layoutInflater, parent, false),
                    quantityDataListHandler
                )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EMPTY ->
                (holder as OrderEmptyViewHolder).bind(isDualPortrait)
            TYPE_RECOMMENDATIONS ->
                (holder as OrderRecommendationsViewHolder).bind(getNumberOfRecommendedItems())
            TYPE_DETAILS_SUBMITTED ->
                (holder as OrderDetailsSubmittedViewHolder).bind(true)
            TYPE_DETAILS ->
                (holder as OrderDetailsViewHolder).bind(
                    true,
                    getOrderItemCount(),
                    getTotalPrice()
                )
            TYPE_DETAILS_SPACE ->
                (holder as OrderDetailsViewHolder).bind(false)
            else -> getOrderItem(position)?.let {
                (holder as OrderItemViewHolder).bind(it, isEditEnabled)
            }
        }
    }

    private fun getOrderItem(position: Int): OrderItem? =
        getOrderItemList()?.get(position - getItemCountBeforeOrderItems())

    override fun getItemViewType(position: Int): Int =
        when {
            position == POSITION_EMPTY_STATE && shouldShowEmptyState() -> TYPE_EMPTY
            position == getRecommendationsPosition() -> TYPE_RECOMMENDATIONS
            position == POSITION_DETAILS_SUBMITTED && shouldShowDetailsSubmitted() -> TYPE_DETAILS_SUBMITTED
            position == POSITION_DETAILS && shouldShowDetails() -> TYPE_DETAILS
            position == POSITION_DETAILS_SPACE && shouldShowDetailsSpace() -> TYPE_DETAILS_SPACE
            else -> TYPE_ITEM
        }

    override fun getItemCount(): Int =
        getOrderItemList().sizeOrZero() + getItemCountBeforeOrderItems() + getItemCountAfterOrderItems()

    private fun getItemCountBeforeOrderItems(): Int {
        var nonOrderItems = 0

        if (shouldShowDetails()) {
            nonOrderItems += 1
        }
        if (shouldShowDetailsSpace()) {
            nonOrderItems += 1
        }
        if (shouldShowDetailsSubmitted()) {
            nonOrderItems += 1
        }
        getRecommendationsPosition()?.let {
            if (it == POSITION_RECOMMENDATIONS) {
                nonOrderItems += 1
            }
        }
        return nonOrderItems
    }

    private fun getItemCountAfterOrderItems(): Int {
        var nonOrderItems = 0
        getRecommendationsPosition()?.let {
            if (it == POSITION_RECOMMENDATIONS_ONE_ITEM) {
                nonOrderItems += 1
            }
        }
        if (shouldShowEmptyState()) {
            nonOrderItems += 1
        }
        return nonOrderItems
    }

    private fun getNumberOfRecommendedItems() =
        when {
            !isDualMode -> RECOMMENDATIONS_SIZE_ONE
            isEditEnabled && isDualMode && getOrderItemList().hasSingleItem() -> RECOMMENDATIONS_SIZE_TWO
            else -> RECOMMENDATIONS_SIZE_THREE
        }

    private fun shouldShowEmptyState() = getOrderItemList().isNullOrEmpty()

    private fun getRecommendationsPosition() =
        if (shouldShowRecommendations()) {
            if (getOrderItemList().hasSingleItem() && !isDualPortrait) {
                POSITION_RECOMMENDATIONS_ONE_ITEM
            } else {
                POSITION_RECOMMENDATIONS
            }
        } else {
            null
        }

    private fun shouldShowRecommendations() =
        getOrderItemList().isNullOrEmpty() || getOrderItemList().hasSingleItem()

    private fun shouldShowDetails() = !getOrderItemList().isNullOrEmpty() && isEditEnabled

    private fun shouldShowDetailsSpace() = isDualPortrait && getOrderItemList().hasMoreThanOneItem()

    private fun shouldShowDetailsSubmitted() = !isEditEnabled

    fun refreshItems() {
        notifyDataSetChanged()
    }

    fun refreshRecommendations() {
        getRecommendationsPosition()?.let {
            notifyItemChanged(it)
        }
    }

    private fun getOrderItemList() = quantityDataListHandler.getDataList()

    private fun getOrderItemCount() = getOrderItemList()?.sumOf { it.quantity } ?: 0

    private fun getTotalPrice() = getOrderItemList()?.sumOf { it.price * it.quantity }?.toFloat() ?: 0f

    class OrderEmptyViewHolder(
        private val binding: OrderEmptyBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(isSingleOnPage: Boolean?) {
            if (isSingleOnPage == true) {
                binding.orderEmptyImage.apply {
                    val params: ViewGroup.LayoutParams = layoutParams
                    params.height = WRAP_CONTENT
                    layoutParams = params
                }
                binding.orderEmptyMessage.textSize = EMPTY_MESSAGE_TEXT_SIZE
            }
            binding.isSingleOnPage = isSingleOnPage
        }
    }

    class OrderRecommendationsViewHolder(
        private val binding: OrderRecommendationsBinding,
        private val recommendationsHandler: DataListHandler<Product>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(itemsToShow: Int?) {
            binding.itemsToShow = itemsToShow

            recommendationsHandler.getDataList()?.takeIf { it.isNotEmpty() }?.let {
                binding.orderRecommendationsItemFirst.bindItem(it[0], recommendationsHandler)
                binding.orderRecommendationsItemSecond.bindItem(it[1], recommendationsHandler)
                binding.orderRecommendationsItemThird.bindItem(it[2], recommendationsHandler)
            }
        }

        private fun ItemProductRecommendationsBinding.bindItem(
            productModel: Product,
            itemClickListener: ItemClickListener<Product>
        ) {
            productImage.setImageBitmap(
                ContextCompat.getDrawable(
                    itemView.context,
                    getProductDrawable(productModel.color, productModel.bodyShape)
                )?.toBitmap()?.rotate(LayoutInfoViewModel.ROTATE_HORIZONTALLY)
            )
            productImage.contentDescription = productImage.context.getString(
                getProductContentDescription(productModel.color, productModel.bodyShape)
            )

            product = productModel
            itemListener = itemClickListener
        }
    }

    class OrderDetailsViewHolder(
        private val binding: ItemOrderDetailsBinding,
        private val itemClickListener: ItemClickListener<Boolean>?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shouldShow: Boolean, itemCount: Int? = null, totalPrice: Float? = null) {
            if (shouldShow) {
                binding.itemListener = itemClickListener
                binding.itemCount = itemCount
                binding.price = totalPrice
            }
            binding.root.isInvisible = !shouldShow
        }
    }

    class OrderDetailsSubmittedViewHolder(
        private val binding: ItemOrderDetailsSubmittedBinding,
        private val submittedOrder: Order?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shouldShow: Boolean) {
            if (shouldShow) {
                binding.timestamp = submittedOrder?.orderTimestamp
                binding.id = submittedOrder?.orderId
                binding.price = submittedOrder?.totalPrice
            }
            binding.root.isInvisible = !shouldShow
        }
    }

    class OrderItemViewHolder(
        private val binding: ItemOrderBinding,
        private val quantityDataListHandler: QuantityDataListHandler<OrderItem>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderItem, isEditEnabled: Boolean) {
            binding.item = item
            binding.productImage.contentDescription = binding.root.context.getString(
                getProductContentDescription(item.color, item.bodyShape)
            )
            binding.isEditEnabled = isEditEnabled
            binding.quantityDataHandler = quantityDataListHandler
            binding.executePendingBindings()
        }
    }

    companion object {
        const val TYPE_EMPTY = -1
        const val TYPE_RECOMMENDATIONS = -2
        const val TYPE_DETAILS_SUBMITTED = -3
        const val TYPE_DETAILS = 1
        const val TYPE_DETAILS_SPACE = 2
        const val TYPE_ITEM = 3

        const val POSITION_EMPTY_STATE = 0
        const val POSITION_DETAILS = 0
        const val POSITION_DETAILS_SPACE = 1
        const val POSITION_DETAILS_SUBMITTED = 0
        const val POSITION_RECOMMENDATIONS = 1
        const val POSITION_RECOMMENDATIONS_ONE_ITEM = 2

        const val RECOMMENDATIONS_SIZE_ONE = 1
        const val RECOMMENDATIONS_SIZE_TWO = 2
        const val RECOMMENDATIONS_SIZE_THREE = 3

        const val EMPTY_MESSAGE_TEXT_SIZE = 20f
    }
}
