package com.alticode.framework.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.alticode.framework.ui.R
import com.alticode.platform.delegation.text
import com.alticode.platform.delegation.value
import com.alticode.platform.delegation.valueFrom
import com.alticode.platform.delegation.valueTo
import com.google.android.material.slider.Slider

class SliderWithText(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_slider_text, this, true)
    }

    private val tvTitle: TextView by lazy { findViewById(R.id.tvTitle) }
    private val tvValue: TextView by lazy { findViewById(R.id.tvValue) }
    val slider: Slider by lazy { findViewById(R.id.slider) }

    var title by tvTitle.text()
    var valueText by tvValue.text()
    var valueFrom by slider.valueFrom()
    var valueTo by slider.valueTo()
    var value by slider.value()
}