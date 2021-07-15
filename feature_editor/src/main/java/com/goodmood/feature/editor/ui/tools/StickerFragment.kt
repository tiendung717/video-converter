package com.goodmood.feature.editor.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentStickerBinding
import com.goodmood.feature.editor.ui.ToolFragment

internal class StickerFragment : ToolFragment() {

    private lateinit var binding: FragmentStickerBinding

    override fun getTitle() = R.string.edit_sticker
    override fun getIcon() = R.drawable.ic_sticker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStickerBinding.inflate(inflater, container, false)
        return binding.root
    }
}