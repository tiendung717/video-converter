package com.goodmood.feature.editor.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentTextBinding
import com.goodmood.feature.editor.repository.model.Text
import com.goodmood.feature.editor.ui.ToolFragment
import com.goodmood.feature.editor.ui.adapter.EditorAdapterFactory
import com.goodmood.feature.editor.ui.adapter.TextAdapterController
import kotlin.random.Random

internal class TextFragment : ToolFragment() {

    private lateinit var binding: FragmentTextBinding
    private lateinit var textAdapterController: TextAdapterController
    private val texts = mutableListOf<Text>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNewText.setOnClickListener { addNewText() }

        textAdapterController = EditorAdapterFactory.createTextAdapter()
        binding.rvText.setController(textAdapterController)
    }

    private fun addNewText() {
        val newText = Text(Random.nextLong(), "Add new text", "", 0, 0, 0)
        texts.add(newText)
        textAdapterController.setData(texts)
        editorViewModel.addNewTextRequest.onNext(newText)
    }
}