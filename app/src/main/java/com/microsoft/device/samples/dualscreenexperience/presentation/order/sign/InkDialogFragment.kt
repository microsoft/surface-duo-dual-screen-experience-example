/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order.sign

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.microsoft.device.dualscreen.utils.wm.getScreenRectangles
import com.microsoft.device.dualscreen.utils.wm.getWindowRect
import com.microsoft.device.dualscreen.utils.wm.normalizeWindowRect
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentInkDialogBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel

class InkDialogFragment : DialogFragment() {

    private var binding: FragmentInkDialogBinding? = null

    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun getDialogSize(): Pair<Float, Float>? {
        if (activity == null) {
            return null
        }

        val screenSize: Rect? = if (layoutInfoViewModel.isDualMode.value != true) {
            requireActivity().getWindowRect()
        } else {
            val layoutInfoRect = layoutInfoViewModel.foldingFeature.value?.bounds
            val windowRect = normalizeWindowRect(
                layoutInfoRect,
                requireActivity().getWindowRect(),
                resources.configuration.orientation
            )
            getScreenRectangles(layoutInfoRect, windowRect)?.firstOrNull()
        }

        if (screenSize == null) {
            return null
        }

        val maximumSize = resources.getDimensionPixelSize(R.dimen.dialog_max_size).toFloat()

        val newWidth = minOf(screenSize.width() * DIALOG_SIZE_WIDTH_PERCENTAGE, maximumSize)
        val newHeight = minOf(screenSize.height() * DIALOG_SIZE_HEIGHT_PERCENTAGE, maximumSize)

        return Pair(newWidth, newHeight)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInkDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        binding?.apply {

            inkDialogContainer.updateLayoutParams {
                width = getDialogSize()?.first?.toInt() ?: 0
                height = getDialogSize()?.second?.toInt() ?: 0
            }

            buttonReset.setOnClickListener {
                inkView.clearInk()
            }

            buttonCancel.setOnClickListener {
                orderViewModel.showSignDialog.value = false
                dismiss()
            }

            buttonConfirm.setOnClickListener {
                if (hasSigned()) {
                    orderViewModel.showSignDialog.value = false
                    dismiss()
                    orderViewModel.submitOrder()
                } else {
                    showNoSignatureError()
                }
            }
        }
    }

    private fun hasSigned(): Boolean {
        val bitmap = binding?.inkView?.saveBitmap()
        val emptyBitmap = Bitmap.createBitmap(
            binding?.inkView?.width ?: 0,
            binding?.inkView?.height ?: 0,
            Bitmap.Config.ARGB_8888
        )
        return (bitmap?.sameAs(emptyBitmap) == false)
    }

    private fun showNoSignatureError() {
        binding?.snackbarContainer?.let {
            val snackbar = Snackbar.make(it, R.string.order_sign_error, Snackbar.LENGTH_SHORT)

            val layoutParams = CoordinatorLayout.LayoutParams(snackbar.view.layoutParams)
            layoutParams.gravity = Gravity.CENTER
            snackbar.view.layoutParams = layoutParams

            context?.let { _ ->
                snackbar.setBackgroundTint(requireContext().getColor(R.color.gold))
                snackbar.setTextColor(requireContext().getColor(R.color.black))
            }
            snackbar.show()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        orderViewModel.showSignDialog.value = false
        super.onCancel(dialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val DIALOG_SIZE_WIDTH_PERCENTAGE = 0.7f
        const val DIALOG_SIZE_HEIGHT_PERCENTAGE = 0.8f
        const val MENU_TO_STROKE_VALUE_RATIO = 3f
        const val STROKE_VALUE_INIT_POS = 2
        const val INK_FRAGMENT_TAG = "ink_dialog"
        const val INK_STROKE_ICON = "INK_STROKE_ICON"
    }
}
