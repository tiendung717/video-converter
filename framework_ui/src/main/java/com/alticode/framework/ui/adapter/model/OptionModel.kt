package com.alticode.framework.ui.adapter.model

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.alticode.framework.ui.R
import com.alticode.framework.ui.R2
import com.alticode.framework.ui.components.SingleChoiceView

@EpoxyModelClass(layout = R2.layout.holder_single_option_model)
abstract class OptionModel : EpoxyModelWithHolder<OptionModel.Holder>() {

    @EpoxyAttribute
    lateinit var option: SingleChoiceView.Option

    @EpoxyAttribute
    lateinit var optionSelected: String

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.tvOption.text = option.text
        holder.icChecked.visibility = if (option.text == optionSelected) View.VISIBLE else View.GONE
        holder.itemView.setOnClickListener(clickListener)
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.itemView.setOnClickListener(null)
    }

    class Holder : EpoxyHolder() {
        lateinit var itemView: View
        lateinit var tvOption: TextView
        lateinit var icChecked: View

        override fun bindView(itemView: View) {
            this.itemView = itemView
            this.tvOption = itemView.findViewById(R.id.tvOption)
            this.icChecked = itemView.findViewById(R.id.checked)
        }

    }
}