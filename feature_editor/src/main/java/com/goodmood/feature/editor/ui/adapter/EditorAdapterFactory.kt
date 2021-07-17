package com.goodmood.feature.editor.ui.adapter

import android.os.Handler
import android.os.HandlerThread

object EditorAdapterFactory {

    fun createTextAdapter(textListener: TextAdapterListener): TextAdapterController {
        val handlerThread = HandlerThread("textAdapter")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        return TextAdapterController(handler, textListener)
    }

    fun createResourceStickerAdapter(onStickerSelected: OnStickerSelected): ResourceStickerAdapterController {
        val handlerThread = HandlerThread("resourceStickerAdapter")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        return ResourceStickerAdapterController(handler, onStickerSelected)
    }

    fun createStickerAdapter(onStickerRemoved: OnStickerRemoved): StickerAdapterController {
        val handlerThread = HandlerThread("stickerAdapter")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        return StickerAdapterController(onStickerRemoved)
    }

}