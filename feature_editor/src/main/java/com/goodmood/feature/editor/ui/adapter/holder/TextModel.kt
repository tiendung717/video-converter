package com.goodmood.feature.editor.ui.adapter.holder

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.*
import com.goodmood.core.editor.R
import com.goodmood.core.editor.R2
import com.goodmood.feature.editor.repository.model.Text

@EpoxyModelClass(layout = R2.layout.holder_text)
abstract class TextModel : EpoxyModelWithHolder<TextModel.Holder>() {

    @EpoxyAttribute
    lateinit var text: Text

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onTextUpdateListener: View.OnClickListener

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var onTextRemoveListener: View.OnClickListener

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.tvText.text = text.text
        holder.itemView.setOnClickListener(onTextUpdateListener)
        holder.btnRemove.setOnClickListener(onTextRemoveListener)
    }

    override fun bind(holder: Holder, previouslyBoundModel: EpoxyModel<*>) {
        if (previouslyBoundModel is TextModel) {
            if (previouslyBoundModel.text.text != text.text) {
                holder.tvText.text = text.text
            }
        }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.itemView.setOnClickListener(null)
        holder.btnRemove.setOnClickListener(null)
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