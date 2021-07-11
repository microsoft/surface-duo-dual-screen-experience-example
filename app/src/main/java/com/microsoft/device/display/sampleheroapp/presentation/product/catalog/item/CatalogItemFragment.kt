/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog.item

import androidx.fragment.app.Fragment
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem

abstract class CatalogItemFragment : Fragment() {

    companion object {
        const val KEY_ITEM_ID = "item_id"
        const val KEY_PAGE_NO = "key_page_no"
        const val KEY_TOTAL_PAGES = "key_total_pages"
    }

    protected val catalogItem: CatalogItem?
        get() {
            return arguments?.getParcelable(KEY_ITEM_ID) as CatalogItem?
        }

    protected val pageNumber: String
        get() {
            return requireActivity().getString(
                R.string.catalog_page_no,
                (arguments?.getInt(KEY_PAGE_NO) ?: 0) + 1,
                arguments?.getInt(KEY_TOTAL_PAGES)
            )
        }
}
