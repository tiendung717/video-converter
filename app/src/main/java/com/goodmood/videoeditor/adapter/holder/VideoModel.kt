package com.goodmood.videoeditor.adapter.holder

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.*
import com.bumptech.glide.Glide
import com.goodmood.core.data.domain.model.Video
import com.goodmood.platform.utils.toTime
import com.goodmood.videoeditor.R
import java.io.File

@EpoxyModelClass(layout = R.layout.holder_video)
abstract class VideoModel : EpoxyModelWithHolder<VideoModel.Holder>() {

    @EpoxyAttribute
    lateinit var video: Video

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    private val metadataRetriever = MediaMetadataRetriever()

    override fun bind(holder: Holder) {
        super.bind(holder)
        Glide
            .with(holder.imgThumbnail)
            .load(Uri.fromFile(File(video.path)))
            .into(holder.imgThumbnail)

        metadataRetriever.setDataSource(video.path)
        val durationStr = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        holder.tvDuration.text = durationStr?.toLong()?.toTime()
        holder.itemView.setOnClickListener(clickListener)
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.itemView.setOnClickListener(null)
        metadataRetriever.release()
    }

    class Holder : EpoxyHolder() {
        lateinit var imgThumbnail: AppCompatImageView
        lateinit var tvDuration: TextView
        lateinit var itemView: View

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.imgThumbnail = itemView.findViewById(R.id.imgThumbnail)
            this.tvDuration = itemView.findViewById(R.id.tvDuration)
        }
    }
}