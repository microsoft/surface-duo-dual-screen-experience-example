/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.launch.fragments

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentLaunchDescriptionBinding
import com.microsoft.device.display.sampleheroapp.presentation.launch.LaunchViewModel

class LaunchDescriptionFragment : Fragment() {

    private val viewModel: LaunchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLaunchDescriptionBinding.inflate(inflater, container, false)
        binding.launchListener = viewModel
        viewModel.isDualMode.observe(viewLifecycleOwner, { binding.shouldDisplayButton = it })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageAnim = view.findViewById<AppCompatImageView>(R.id.launch_pattern_showcase)
        buildShowcaseAnimation(buildAnimationSteps(requireContext())).let {
            imageAnim.setImageDrawable(it)
            it.start()
        }
    }

    private fun buildAnimationSteps(context: Context) = listOf(
        ContextCompat.getDrawable(context, R.drawable.extended_canvas),
        ContextCompat.getDrawable(context, R.drawable.list_detail),
        ContextCompat.getDrawable(context, R.drawable.two_page),
        ContextCompat.getDrawable(context, R.drawable.dual_view),
        ContextCompat.getDrawable(context, R.drawable.companion_pane)
    )

    private fun buildShowcaseAnimation(drawableList: List<Drawable?>) =
        AnimationDrawable().apply {
            drawableList.filterNotNull().forEach {
                addFrame(it, ANIMATION_DURATION)
            }

            isOneShot = false
        }

    companion object {
        const val ANIMATION_DURATION = 600
    }
}
