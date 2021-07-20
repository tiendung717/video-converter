package com.alticode.framework.ui.components.video.adapter.model

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.alticode.framework.ui.R
import com.alticode.framework.ui.R2
import com.alticode.platform.delegation.text
import com.alticode.platform.utils.toTime
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MetadataRetriever
import java.io.File

@EpoxyModelClass(layout = R2.layout.holder_video_item)
abstract class VideoItemModel : EpoxyModelWithHolder<VideoItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var videoPath: String

    private val metadataRetriever by lazy { MediaMetadataRetriever() }

    override fun bind(holder: Holder) {
        super.bind(holder)

        val videoFile = File(videoPath)
        Glide.with(holder.thumbnailImageView)
            .load(Uri.fromFile(videoFile))
            .into(holder.thumbnailImageView)

        metadataRetriever.setDataSource(videoPath)
        holder.tvName.text = videoFile.name
        holder.tvInfo.text =
            metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                .orEmpty()
                .toLong()
                .toTime()
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        metadataRetriever.release()
    }

    class Holder : EpoxyHolder() {
        lateinit var thumbnailImageView: ImageView
        lateinit var tvName: TextView
        lateinit var tvInfo: TextView


        override fun bindView(itemView: View) {
            thumbnailImageView = itemView.findViewById(R.id.videoThumbnail)
            tvName = itemView.findViewById(R.id.tvName)
            tvInfo = itemView.findViewById(R.id.tvInfo)
        }
    }
}