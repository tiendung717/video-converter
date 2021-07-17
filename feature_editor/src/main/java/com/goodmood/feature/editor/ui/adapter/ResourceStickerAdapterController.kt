package com.goodmood.feature.editor.ui.adapter

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.goodmood.feature.editor.ui.adapter.holder.resourceSticker

typealias OnStickerSelected = (stickerPath: String) -> Unit

class ResourceStickerAdapterController(handler: Handler, private val onStickerSelected: OnStickerSelected) :
    TypedEpoxyController<List<String>>(handler, handler) {

    override fun buildModels(data: List<String>) {
        data.forEach {
            resourceSticker {
                id(it.hashCode())
                stickerPath(it)
                clickListener { _, _, _, _ ->
                    onStickerSelected.invoke(it)
                }
            }
        }
    }
}