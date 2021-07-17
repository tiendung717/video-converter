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
import com.goodmood.feature.editor.repository.model.Sticker
import java.io.File

@EpoxyModelClass(layout = R2.layout.holder_sticker)
abstract class StickerModel : EpoxyModelWithHolder<StickerModel.Holder>() {

    @EpoxyAttribute
    lateinit var sticker: Sticker

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.btnRemoveSticker.setOnClickListener(clickListener)
        Glide.with(holder.imgSticker)
            .load(Uri.fromFile(File(sticker.path)))
            .into(holder.imgSticker)
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.btnRemoveSticker.setOnClickListener(null)
    }

    class Holder : EpoxyHolder() {
        lateinit var itemView: View
        lateinit var imgSticker: ImageView
        lateinit var btnRemoveSticker: View

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.imgSticker = itemView.findViewById(R.id.imgSticker)
            this.btnRemoveSticker = itemView.findViewById(R.id.btnRemove)
        }
    }
}