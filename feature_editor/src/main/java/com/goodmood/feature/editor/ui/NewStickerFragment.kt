package com.goodmood.feature.editor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.goodmood.core.editor.databinding.FragmentNewStickerBinding
import com.goodmood.feature.editor.repository.model.Sticker
import com.goodmood.feature.editor.ui.adapter.EditorAdapterFactory
import com.goodmood.feature.editor.viewmodel.EditorViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.random.Random

class NewStickerFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentNewStickerBinding
    private val editorViewModel: EditorViewModel by activityViewModels()
    private val stickerAdapterController by lazy {
        EditorAdapterFactory.createResourceStickerAdapter {
            val newSticker = Sticker(Random.nextLong(), it, 0.5f, 0.5f)
            editorViewModel.updateTool(newSticker)
            dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewStickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSticker.setController(stickerAdapterController)
        stickerAdapterController.setData(editorViewModel.getStickerFiles())
    }
}