package com.goodmood.feature.editor.ui.adapter.holder

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.goodmood.core.editor.R
import com.goodmood.core.editor.R2
import com.goodmood.feature.editor.repository.model.Text

@EpoxyModelClass(layout = R2.layout.holder_text)
abstract class TextModel : EpoxyModelWithHolder<TextModel.Holder>() {

    @EpoxyAttribute
    lateinit var text: Text

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.tvText.text = text.text
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
    }

    class Holder : EpoxyHolder() {
        lateinit var itemView: View
        lateinit var tvText: TextView
        lateinit var btnRemove: View

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.tvText = itemView.findViewById(R.id.tvText)
            this.btnRemove = itemView.findViewById(R.id.btnRemove)
        }
    }
}