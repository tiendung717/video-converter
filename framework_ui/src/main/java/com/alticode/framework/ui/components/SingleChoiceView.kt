package com.alticode.framework.ui.components

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.alticode.framework.ui.R
import com.alticode.framework.ui.adapter.AdapterFactory
import com.alticode.framework.ui.adapter.controller.OptionClickListener
import com.alticode.platform.delegation.text
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias OnOptionChangedListener = (option: SingleChoiceView.Option) -> Unit

class SingleChoiceView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    class Option(val text: String, val value: Serializable) : Serializable

    init {
        LayoutInflater.from(context).inflate(R.layout.view_single_choice, this, true)
        setOnClickListener {
            SingleChoiceDialog(
                context = context,
                title = title,
                selectedText = valueText,
                options = allOptions,
                optionClickListener = {
                    this.valueText = it.text
                    optionChangedListener?.invoke(it)
                }
            ).show()
        }
    }

    private val tvTitle: TextView by lazy { findViewById(R.id.tvTitle) }
    private val tvValue: TextView by lazy { findViewById(R.id.tvValue) }

    var title by tvTitle.text()
    var valueText by tvValue.text()
    var optionList by optionList()
    val selectedOption by currentOption()
    var optionChangedListener: OnOptionChangedListener? = null
    var allOptions = listOf<Option>()

    fun setOptions(options: List<Option>) {
        this.valueText = options[0].text
        this.allOptions = options
    }

    fun getCurrentValue() = allOptions.find { it.text == valueText }?.value

}

internal class SingleChoiceDialog(
    context: Context,
    val title: String,
    val options: List<SingleChoiceView.Option>,
    val selectedText: String,
    val optionClickListener: OptionClickListener
) : BottomSheetDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_single_choice)

        setup()
    }

    private fun setup() {
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val rvOption = findViewById<EpoxyRecyclerView>(R.id.rvOption)
        tvTitle?.text = title

        val optionController = AdapterFactory.createSingleOptionController {
            optionClickListener.invoke(it)
            dismiss()
        }
        rvOption?.setController(optionController)
        optionController.setData(selectedText, options)
    }
}

fun SingleChoiceView.optionList(): ReadWriteProperty<Any, List<SingleChoiceView.Option>> {
    val singleChoiceView = this
    return object : ReadWriteProperty<Any, List<SingleChoiceView.Option>> {
        override fun getValue(thisRef: Any, property: KProperty<*>): List<SingleChoiceView.Option> {
            return singleChoiceView.allOptions
        }

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: List<SingleChoiceView.Option>
        ) {
            singleChoiceView.setOptions(value)
        }
    }
}

fun SingleChoiceView.title(): ReadWriteProperty<Any, String> {
    val singleChoiceView = this
    return object : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return singleChoiceView.title
        }

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: String
        ) {
            singleChoiceView.title = value
        }
    }
}

fun SingleChoiceView.currentOption(): ReadOnlyProperty<Any, Serializable?> {
    return ReadOnlyProperty { _, _ -> getCurrentValue() }
}