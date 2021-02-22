/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.display.sampleheroapp.databinding.ItemStoreBinding
import com.microsoft.device.display.sampleheroapp.domain.store.model.Store
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListHandler
import com.microsoft.device.display.sampleheroapp.presentation.util.ItemClickListener

class StoreAdapter(
    context: Context,
    private val dataHandler: DataListHandler<Store>
) : RecyclerView.Adapter<StoreAdapter.DummyViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DummyViewHolder(
            ItemStoreBinding.inflate(layoutInflater, parent, false),
            dataHandler
        )

    override fun onBindViewHolder(holder: DummyViewHolder, position: Int) {
        dataHandler.getDataList()?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = dataHandler.getDataList()?.size ?: 0

    fun refreshData() {
        notifyDataSetChanged()
    }

    class DummyViewHolder(
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
