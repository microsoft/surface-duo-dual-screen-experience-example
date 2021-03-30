/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.sampleheroapp.databinding.ItemProductBinding
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListHandler
import com.microsoft.device.display.sampleheroapp.presentation.util.ItemClickListener

class ProductAdapter(
    context: Context,
    private val dataHandler: DataListHandler<Product>
) : RecyclerView.Adapter<ProductAdapter.DummyViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DummyViewHolder(
            ItemProductBinding.inflate(layoutInflater, parent, false),
            dataHandler
        )

    override fun onBindViewHolder(holder: DummyViewHolder, position: Int) {
        dataHandler.getDataList()?.get(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = dataHandler.getDataList()?.size ?: 0

    fun refreshData() {
        notifyDataSetChanged()
    }

    class DummyViewHolder(
        private val binding: ItemProductBinding,
        private val itemClickListener: ItemClickListener<Product>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.product = product
            binding.itemListener = itemClickListener
            binding.executePendingBindings()
        }
    }
}
