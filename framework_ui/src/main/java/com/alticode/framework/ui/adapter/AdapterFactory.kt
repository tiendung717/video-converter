package com.alticode.framework.ui.adapter

import android.os.Handler
import android.os.HandlerThread
import com.alticode.framework.ui.adapter.controller.OptionClickListener
import com.alticode.framework.ui.adapter.controller.SingleOptionController
import com.alticode.framework.ui.components.video.adapter.VideoListController

object AdapterFactory {
    fun createSingleOptionController(optionClickListener: OptionClickListener): SingleOptionController {
        val handlerThread = HandlerThread("SingleOptionController")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        return SingleOptionController(handler, optionClickListener)
    }
}