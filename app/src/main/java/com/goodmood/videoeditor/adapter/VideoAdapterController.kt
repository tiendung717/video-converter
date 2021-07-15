package com.goodmood.videoeditor.adapter

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.goodmood.core.data.domain.model.Video
import com.goodmood.videoeditor.adapter.holder.video
import com.goodmood.videoeditor.adapter.holder.videoEmpty

typealias VideoClickListener = (video: Video) -> Unit
class VideoAdapterController(handler: Handler, private val videoClickListener: VideoClickListener) : TypedEpoxyController<List<Video>>(handler, handler) {

    override fun buildModels(data: List<Video>) {
        if (data.isEmpty()) {
            videoEmpty {
                id(0)
            }
        } else {
            data.forEach {
                video {
                    id(it.path.hashCode())
                    video(it)
                    clickListener { _, _, _, _ ->
                        videoClickListener.invoke(it)
                    }
                }
            }
        }
    }
}