package com.alticode.framework.ui.components.video.adapter

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.alticode.framework.ui.components.video.adapter.model.videoItem

typealias OnVideoRemoveListener = (path: String) -> Unit

class VideoListController(handler: Handler, private val onVideoRemoveListener: OnVideoRemoveListener) : TypedEpoxyController<List<String>>(handler, handler) {

    override fun buildModels(data: List<String>) {
        data.forEach {
            videoItem {
                id(it.hashCode())
                videoPath(it)
                clickListener { _, _, _, _ ->
                    onVideoRemoveListener.invoke(it)
                }
            }
        }
    }
}