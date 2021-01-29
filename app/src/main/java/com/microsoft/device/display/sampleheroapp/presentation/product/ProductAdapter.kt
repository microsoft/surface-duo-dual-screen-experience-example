/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.sampleheroapp.databinding.ItemProductBinding
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product

class ProductAdapter(
    context: Context,
    private val viewModel: ProductViewModel
) : RecyclerView.Adapter<ProductAdapter.DummyViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DummyViewHolder(
            ItemProductBinding.inflate(layoutInflater, parent, false),
            viewModel
        )

    override fun onBindViewHolder(holder: DummyViewHolder, position: Int) {
        viewModel.productList.value?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = viewModel.productList.value?.size ?: 0

    fun refreshData() {
        notifyDataSetChanged()
    }

    class DummyViewHolder(
        private val binding: ItemProductBinding,
        private val viewModel: ProductViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
            binding.itemListener = viewModel
            binding.executePendingBindings()
        }
    }
}
