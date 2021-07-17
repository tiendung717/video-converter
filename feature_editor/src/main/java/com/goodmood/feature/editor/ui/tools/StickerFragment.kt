package com.goodmood.feature.editor.ui.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goodmood.core.editor.R
import com.goodmood.core.editor.databinding.FragmentStickerBinding
import com.goodmood.feature.editor.repository.model.Sticker
import com.goodmood.feature.editor.ui.NewStickerFragment
import com.goodmood.feature.editor.ui.ToolFragment
import com.goodmood.feature.editor.ui.adapter.EditorAdapterFactory
import com.goodmood.feature.editor.ui.adapter.StickerAdapterController
import com.goodmood.platform.log.AppLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class StickerFragment : ToolFragment() {

    private lateinit var binding: FragmentStickerBinding

    override fun getTitle() = R.string.edit_sticker
    override fun getIcon() = R.drawable.ic_sticker

    private val stickers = mutableListOf<Sticker>()
    private val stickerAdapterController: StickerAdapterController by lazy {
        EditorAdapterFactory.createStickerAdapter {
            stickers.removeAll { sticker -> sticker.toolId == it.toolId }
            stickerAdapterController.setData(stickers)
            editorViewModel.removeTool(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNewSticker.setOnClickListener {
            NewStickerFragment().show(childFragmentManager, "")
        }

        binding.rvSticker.setController(stickerAdapterController)
        observeStickerAdded()
        observeStickerRemoved()
    }

    private fun observeStickerRemoved() {
        disposable.add(
            editorViewModel.observeStickerDeleted()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        stickers.removeAll { sticker -> sticker.toolId == it.toolId }
                        stickerAdapterController.setData(stickers)
                    },
                    { AppLog.e("Observe sticker deleted failed. ${it.message}") })
        )
    }

    private fun observeStickerAdded() {
        disposable.add(
            editorViewModel.observeStickerUpdated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        stickers.add(it)
                        stickerAdapterController.setData(stickers)
                    },
                    { AppLog.e("Observe new sticker added failed. ${it.message}") })
        )
    }
}