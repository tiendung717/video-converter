package com.alticode.videoeditor.adapter

import android.os.Handler
import android.os.HandlerThread

object AdapterFactory {

    fun createVideoAdapter(mediaClickListener: MediaClickListener): VideoAdapterController {
        val handlerThread = HandlerThread("mediaAdapter")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        return VideoAdapterController(handler, mediaClickListener)
    }
}