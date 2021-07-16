package com.goodmood.feature.editor.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentTextBinding
import com.goodmood.feature.editor.repository.model.Text
import com.goodmood.feature.editor.ui.NewTextFragment
import com.goodmood.feature.editor.ui.ToolFragment
import com.goodmood.feature.editor.ui.adapter.EditorAdapterFactory
import com.goodmood.feature.editor.ui.adapter.TextAdapterController
import com.goodmood.feature.editor.ui.adapter.TextAdapterListener
import com.goodmood.platform.log.AppLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class TextFragment : ToolFragment(), TextAdapterListener {

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

        textAdapterController = EditorAdapterFactory.createTextAdapter(this)
        binding.rvText.setController(textAdapterController)

        observeNewTextAdded()
        observeTextRemoved()
    }

    private fun observeTextRemoved() {
        disposable.add(
            editorViewModel.observeTextDeleted()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        texts.removeAll { text -> text.toolId == it.toolId }
                        textAdapterController.setData(texts)
                    },
                    { AppLog.e("Observe text deleted failed. ${it.message}") })
        )
    }

    private fun observeNewTextAdded() {
        disposable.add(
            editorViewModel.observeTextUpdated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { addOrUpdateTextList(it) },
                    { AppLog.e("Observe new text added failed. ${it.message}") })
        )
    }

    private fun addOrUpdateTextList(newText: Text) {
        val index = texts.indexOfFirst { it.toolId == newText.toolId }
        if (index >= 0) {
            texts.removeAt(index)
            texts[index] = newText
        } else {
            texts.add(newText)
        }
        textAdapterController.setData(texts)
    }

    private fun addNewText() {
        val newTextFragment = NewTextFragment.newInstance()
        newTextFragment.show(childFragmentManager, "")
    }

    override fun onTextUpdate(text: Text) {
        val newTextFragment = NewTextFragment.newInstance(text)
        newTextFragment.show(childFragmentManager, "")
    }

    override fun onTextRemove(text: Text) {
        editorViewModel.removeTool(text)
    }
}