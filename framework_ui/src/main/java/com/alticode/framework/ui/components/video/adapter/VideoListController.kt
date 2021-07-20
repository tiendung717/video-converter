package com.alticode.framework.ui.components.video.adapter

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.alticode.framework.ui.components.video.adapter.model.videoItem

class VideoListController(handler: Handler) : TypedEpoxyController<List<String>>(handler, handler) {

    override fun buildModels(data: List<String>) {
        data.forEach {
            videoItem {
                id(it.hashCode())
                videoPath(it)
            }
        }
    }
}