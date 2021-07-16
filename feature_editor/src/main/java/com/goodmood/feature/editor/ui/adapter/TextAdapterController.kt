package com.goodmood.feature.editor.ui.adapter

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.goodmood.feature.editor.repository.model.Text
import com.goodmood.feature.editor.ui.adapter.holder.text

class TextAdapterController(handler: Handler) : TypedEpoxyController<List<Text>>(handler, handler) {
    override fun buildModels(data: List<Text>) {
        data.forEach {
            text {
                id(it.toolId)
                text(it)
            }
        }
    }
}