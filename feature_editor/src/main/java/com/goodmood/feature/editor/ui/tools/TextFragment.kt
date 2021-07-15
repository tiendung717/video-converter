package com.goodmood.feature.editor.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentTextBinding
import com.goodmood.feature.editor.ui.ToolFragment

internal class TextFragment : ToolFragment() {

    private lateinit var binding: FragmentTextBinding

    override fun getTitle() = R.string.edit_text
    override fun getIcon() = R.drawable.ic_text

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTextBinding.inflate(inflater, container, false)
        return binding.root
    }
}