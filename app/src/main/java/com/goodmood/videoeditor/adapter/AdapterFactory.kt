package com.goodmood.videoeditor.adapter

import android.os.Handler
import android.os.HandlerThread

object AdapterFactory {

    fun createVideoAdapter(videoClickListener: VideoClickListener): VideoAdapterController {
        val handlerThread = HandlerThread("videoAdapter")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        return VideoAdapterController(handler, videoClickListener)
    }
}