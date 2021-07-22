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

class SingleChoiceView(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    class Option(val text: String, val value: Serializable) : Serializable

    init {
        LayoutInflater.from(context).inflate(R.layout.view_single_choice, this, true)
    }

    private val tvTitle: TextView by lazy { findViewById(R.id.tvTitle) }
    private val tvValue: TextView by lazy { findViewById(R.id.tvValue) }
    private val options by lazy { mutableListOf<Option>() }
    private var defOption: Option? = null

    private var title by tvTitle.text()
    private var valueText by tvValue.text()

    fun setData(title: String, options: List<Option>, defOption: Option? = null) {
        this.title = title
        this.defOption = defOption
        this.valueText = defOption?.text.orEmpty()
        this.options.addAll(options)

        setOnClickListener { showOptionDialog() }
    }

    fun getCurrentValue() = options.find { it.text == valueText }?.value

    private fun showOptionDialog() {
        SingleChoiceDialog(
            context = context,
            title = title,
            defOption = defOption,
            options = options,
            optionClickListener = {
                valueText = it.text
                defOption = it
            }
        ).show()
    }
}

internal class SingleChoiceDialog(
    context: Context,
    val title: String,
    val options: List<SingleChoiceView.Option>,
    val defOption: SingleChoiceView.Option? = null,
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

        val defText = defOption?.text.orEmpty()
        optionController.setData(defText, options)
    }
}