package com.goodmood.feature.editor.ui.adapter

import android.os.Handler
import android.os.HandlerThread

object EditorAdapterFactory {

    fun createTextAdapter(): TextAdapterController {
        val handlerThread = HandlerThread("textAdapter")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        return TextAdapterController(handler)
    }

}