package com.alticode.videoeditor.adapter.holder

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.alticode.core.data.domain.model.Media
import com.alticode.platform.utils.toTime
import com.alticode.videoeditor.R
import java.io.File

@EpoxyModelClass(layout = R.layout.holder_media)
abstract class MediaModel : EpoxyModelWithHolder<MediaModel.Holder>() {

    @EpoxyAttribute
    lateinit var media: Media

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    private val metadataRetriever = MediaMetadataRetriever()

    override fun bind(holder: Holder) {
        super.bind(holder)
        Glide
            .with(holder.imgThumbnail)
            .load(Uri.fromFile(File(media.path)))
            .into(holder.imgThumbnail)

        metadataRetriever.setDataSource(media.path)
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