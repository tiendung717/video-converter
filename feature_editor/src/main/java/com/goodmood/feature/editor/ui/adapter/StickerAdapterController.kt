package com.goodmood.feature.editor.ui.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.goodmood.feature.editor.repository.model.Sticker
import com.goodmood.feature.editor.ui.adapter.holder.sticker

typealias OnStickerRemoved = (sticker: Sticker) -> Unit

class StickerAdapterController(private val onStickerRemoved: OnStickerRemoved) :
    TypedEpoxyController<List<Sticker>>() {

    override fun buildModels(data: List<Sticker>) {
        data.forEach {
            sticker {
                id(it.toolId)
                sticker(it)
                clickListener { _, _, _, _ ->
                    onStickerRemoved.invoke(it)
                }
            }
        }
    }
}