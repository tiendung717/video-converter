package com.alticode.videoeditor.adapter.holder

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.alticode.videoeditor.R

@EpoxyModelClass(layout = R.layout.holder_media_empty)
abstract class MediaEmptyModel : EpoxyModelWithHolder<MediaEmptyModel.Holder>() {

    class Holder : EpoxyHolder() {

        override fun bindView(itemView: View) {

        }
    }
}