package com.goodmood.videoeditor.adapter.holder

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.goodmood.videoeditor.R

@EpoxyModelClass(layout = R.layout.holder_video_empty)
abstract class VideoEmptyModel : EpoxyModelWithHolder<VideoEmptyModel.Holder>() {

    class Holder : EpoxyHolder() {

        override fun bindView(itemView: View) {

        }
    }
}