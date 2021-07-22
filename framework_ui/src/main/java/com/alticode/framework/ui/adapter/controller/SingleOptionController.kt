package com.alticode.framework.ui.adapter.controller

import android.os.Handler
import com.airbnb.epoxy.Typed2EpoxyController
import com.alticode.framework.ui.adapter.model.option
import com.alticode.framework.ui.components.SingleChoiceView

typealias OptionClickListener = (option: SingleChoiceView.Option) -> Unit

class SingleOptionController(handler: Handler, private val optionClickListener: OptionClickListener) :
    Typed2EpoxyController<String, List<SingleChoiceView.Option>>(handler, handler) {

    override fun buildModels(optionSelected: String, data: List<SingleChoiceView.Option>) {
        data.forEach {
            option {
                id(it.text)
                option(it)
                optionSelected(optionSelected)
                clickListener { _, _, _, _ ->
                    optionClickListener.invoke(it)
                }
            }
        }
    }
}