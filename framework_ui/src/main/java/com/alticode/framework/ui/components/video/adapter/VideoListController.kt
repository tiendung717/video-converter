package com.alticode.framework.ui.components.video.adapter

import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Typed2EpoxyController
import com.alticode.framework.ui.components.video.adapter.model.videoItemHorizontal
import com.alticode.framework.ui.components.video.adapter.model.videoItemVertical

typealias OnVideoRemoveListener = (path: String) -> Unit

class VideoListController(
    handler: Handler,
    private val onVideoRemoveListener: OnVideoRemoveListener
) : Typed2EpoxyController<Int, List<String>>(handler, handler) {

    override fun buildModels(orientation: Int, data: List<String>) {
        data.forEach {
            if (orientation == RecyclerView.VERTICAL) {
                videoItemVertical {
                    id(it.hashCode())
                    videoPath(it)
                    clickListener { _, _, _, _ ->
                        onVideoRemoveListener.invoke(it)
                    }
                }
            } else {
                videoItemHorizontal {
                    id(it.hashCode())
                    videoPath(it)
                    clickListener { _, _, _, _ ->
                        onVideoRemoveListener.invoke(it)
                    }
                }
            }
        }
    }
}