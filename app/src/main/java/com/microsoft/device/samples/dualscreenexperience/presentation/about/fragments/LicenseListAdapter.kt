/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.device.samples.dualscreenexperience.databinding.ItemLicenseBinding
import com.microsoft.device.samples.dualscreenexperience.domain.about.model.License
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListHandler
import com.microsoft.device.samples.dualscreenexperience.presentation.util.ItemClickListener
import com.microsoft.device.samples.dualscreenexperience.presentation.util.sizeOrZero

class LicenseListAdapter(
    context: Context,
    private val licensesHandler: DataListHandler<License?>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        LicenseViewHolder(
            ItemLicenseBinding.inflate(layoutInflater, parent, false),
            licensesHandler
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LicenseViewHolder).bind(licensesHandler.getDataList()?.get(position))
    }

    override fun getItemCount(): Int = licensesHandler.getDataList().sizeOrZero()

    class LicenseViewHolder(
        private val binding: ItemLicenseBinding,
        private val itemClickListener: ItemClickListener<License?>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: License?) {
            binding.item = item
            binding.itemClickListener = itemClickListener
            binding.executePendingBindings()
        }
    }
}
