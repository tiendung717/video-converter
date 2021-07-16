package com.goodmood.feature.editor.ui.adapter

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.goodmood.feature.editor.repository.model.Text
import com.goodmood.feature.editor.ui.adapter.holder.text

interface TextAdapterListener {
    fun onTextUpdate(text: Text)
    fun onTextRemove(text: Text)
}

class TextAdapterController(handler: Handler, private val textListener: TextAdapterListener) : TypedEpoxyController<List<Text>>(handler, handler) {
    override fun buildModels(data: List<Text>) {
        data.forEach {
            text {
                id(it.toolId)
                text(it)
                onTextRemoveListener { model, parentView, clickedView, position ->
                    textListener.onTextRemove(it)
                }
                onTextUpdateListener { model, parentView, clickedView, position ->
                    textListener.onTextUpdate(it)
                }
            }
        }
    }
}