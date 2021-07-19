package com.alticode.videoeditor.adapter

import android.os.Handler
import com.airbnb.epoxy.TypedEpoxyController
import com.alticode.core.data.domain.model.Media
import com.alticode.videoeditor.adapter.holder.media
import com.alticode.videoeditor.adapter.holder.mediaEmpty

typealias MediaClickListener = (media: Media) -> Unit
class VideoAdapterController(handler: Handler, private val mediaClickListener: MediaClickListener) : TypedEpoxyController<List<Media>>(handler, handler) {

    override fun buildModels(data: List<Media>) {
        if (data.isEmpty()) {
            mediaEmpty {
                id(0)
            }
        } else {
            data.forEach {
                media {
                    id(it.path.hashCode())
                    media(it)
                    clickListener { _, _, _, _ ->
                        mediaClickListener.invoke(it)
                    }
                }
            }
        }
    }
}