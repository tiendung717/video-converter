package com.alticode.framework.ui.components.video.adapter

import android.os.Handler
import android.os.HandlerThread

object AdapterFactory {

    fun createVideoListAdapter(): VideoListController {
        val handlerThread = HandlerThread("VideoListController")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        return VideoListController(handler)
    }
}