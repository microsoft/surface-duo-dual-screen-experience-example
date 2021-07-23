/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.samples.dualscreenexperience.databinding.ItemStoreBinding
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.Store
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListHandler
import com.microsoft.device.samples.dualscreenexperience.presentation.util.ItemClickListener

class StoreAdapter(
    context: Context,
    private val dataHandler: DataListHandler<Store>
) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StoreViewHolder(
            ItemStoreBinding.inflate(layoutInflater, parent, false),
            dataHandler
        )

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        dataHandler.getDataList()?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = dataHandler.getDataList()?.size ?: 0

    fun refreshData() {
        notifyDataSetChanged()
    }

    class StoreViewHolder(
        private val binding: ItemStoreBinding,
        private val itemClickListener: ItemClickListener<Store>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store) {
            binding.store = store
            binding.itemListener = itemClickListener
            binding.storeRatingReviews.store = store
            binding.executePendingBindings()
        }
    }
}
