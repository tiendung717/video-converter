package com.goodmood.feature.editor.ui.adapter.holder

import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.goodmood.core.editor.R
import com.goodmood.core.editor.R2
import java.io.File

@EpoxyModelClass(layout = R2.layout.holder_resource_sticker)
abstract class ResourceStickerModel : EpoxyModelWithHolder<ResourceStickerModel.Holder>() {

    @EpoxyAttribute
    lateinit var stickerPath: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.itemView.setOnClickListener(clickListener)
        Glide.with(holder.imgSticker)
            .load(Uri.fromFile(File(stickerPath)))
            .into(holder.imgSticker)

    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.itemView.setOnClickListener(null)
    }

    class Holder : EpoxyHolder() {
        lateinit var itemView: View
        lateinit var imgSticker: ImageView

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.imgSticker = itemView.findViewById(R.id.imgSticker)
        }
    }
}