package com.goodmood.videoeditor.adapter.holder

import android.net.Uri
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.*
import com.bumptech.glide.Glide
import com.goodmood.core.data.domain.model.Video
import com.goodmood.videoeditor.R
import java.io.File

@EpoxyModelClass(layout = R.layout.holder_video)
abstract class VideoModel : EpoxyModelWithHolder<VideoModel.Holder>() {

    @EpoxyAttribute
    lateinit var video: Video

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.itemView.setOnClickListener(clickListener)
        Glide
            .with(holder.imgThumbnail)
            .load(Uri.fromFile(File(video.path)))
            .into(holder.imgThumbnail)
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.itemView.setOnClickListener(null)
    }

    class Holder : EpoxyHolder() {
        lateinit var imgThumbnail: AppCompatImageView
        lateinit var itemView: View

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.imgThumbnail = itemView.findViewById(R.id.imgThumbnail)
        }
    }
}