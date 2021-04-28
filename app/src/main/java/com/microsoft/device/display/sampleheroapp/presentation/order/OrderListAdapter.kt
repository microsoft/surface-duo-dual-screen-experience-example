/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.sampleheroapp.databinding.ItemOrderBinding
import com.microsoft.device.display.sampleheroapp.databinding.ItemOrderDetailsBinding
import com.microsoft.device.display.sampleheroapp.databinding.ItemProductRecommendationsBinding
import com.microsoft.device.display.sampleheroapp.databinding.OrderEmptyBinding
import com.microsoft.device.display.sampleheroapp.databinding.OrderRecommendationsBinding
import com.microsoft.device.display.sampleheroapp.domain.order.model.OrderItem
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductDrawable
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListHandler
import com.microsoft.device.display.sampleheroapp.presentation.util.ItemClickListener
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.rotate

class OrderListAdapter(
    context: Context,
    private val viewModel: OrderViewModel,
    private val recommendationsHandler: DataListHandler<Product>,
    var isDualMode: Boolean? = null,
    var isDualPortrait: Boolean? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    private var isEditEnabled = true

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
                    viewModel
                )
            else ->
                OrderItemViewHolder(
                    ItemOrderBinding.inflate(layoutInflater, parent, false),
                    viewModel
                )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EMPTY -> (holder as OrderEmptyViewHolder).bind(isDualPortrait)
            TYPE_RECOMMENDATIONS -> (holder as OrderRecommendationsViewHolder).bind(isDualMode)
            TYPE_DETAILS -> (holder as OrderDetailsViewHolder).bind(true)
            TYPE_DETAILS_SPACE -> (holder as OrderDetailsViewHolder).bind(false)
            else -> getOrderItem(position)?.let {
                (holder as OrderItemViewHolder).bind(it, isEditEnabled)
            }
        }
    }

    private fun getOrderItem(position: Int): OrderItem? =
        when (isDualPortrait) {
            true -> viewModel.getDataList()?.get(position - 2)
            else -> viewModel.getDataList()?.get(position - 1)
        }

    override fun getItemViewType(position: Int): Int =
        when {
            viewModel.getDataList()?.isEmpty() != false && position == 0 -> TYPE_EMPTY
            viewModel.getDataList()?.isEmpty() != false && position == 1 -> TYPE_RECOMMENDATIONS
            position == 0 -> TYPE_DETAILS
            position == 1 && isDualPortrait == true -> TYPE_DETAILS_SPACE
            else -> TYPE_ITEM
        }

    override fun getItemCount(): Int =
        when {
            viewModel.getDataList()?.isEmpty() != false -> 2
            isDualPortrait == true -> (viewModel.getDataList()?.size ?: 0) + 2
            else -> (viewModel.getDataList()?.size ?: 0) + 1
        }

    fun refreshItems() {
        notifyDataSetChanged()
    }

    fun refreshRecommendations() {
        if (viewModel.getDataList()?.isEmpty() != false) {
            notifyItemChanged(1)
        }
    }

    fun disableEditing() {
        isEditEnabled = false
        notifyDataSetChanged()
    }

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
                binding.orderEmptyMessage.textSize = 20f
            }
        }
    }

    class OrderRecommendationsViewHolder(
        private val binding: OrderRecommendationsBinding,
        private val recommendationsHandler: DataListHandler<Product>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shouldShowMoreItems: Boolean?) {
            binding.shouldShowMoreItems = shouldShowMoreItems

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
                )?.toBitmap()?.rotate(RotationViewModel.ROTATE_HORIZONTALLY)
            )

            product = productModel
            itemListener = itemClickListener
        }
    }

    class OrderDetailsViewHolder(
        private val binding: ItemOrderDetailsBinding,
        private val viewModel: OrderViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shouldShow: Boolean) {
            if (shouldShow) {
                binding.viewModel = viewModel
                binding.root.visibility = View.VISIBLE
            } else {
                binding.root.visibility = View.INVISIBLE
                binding.viewModel = null
            }
        }
    }

    class OrderItemViewHolder(
        private val binding: ItemOrderBinding,
        private val viewModel: OrderViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OrderItem, isEditEnabled: Boolean) {
            binding.item = item
            binding.isEditEnabled = isEditEnabled
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    companion object {
        const val TYPE_EMPTY = -1
        const val TYPE_RECOMMENDATIONS = -2
        const val TYPE_DETAILS = 1
        const val TYPE_DETAILS_SPACE = 2
        const val TYPE_ITEM = 3
    }
}
