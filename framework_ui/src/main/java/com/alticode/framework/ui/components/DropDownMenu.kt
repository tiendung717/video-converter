package com.alticode.framework.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RelativeLayout
import com.alticode.framework.ui.R
import com.google.android.material.textfield.TextInputLayout

class DropDownMenu(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    class Item(val text: String, val value: Any)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_dropdown_menu, this, true)
    }

    private val tvAutoComplete: AutoCompleteTextView by lazy { findViewById(R.id.tvAutoComplete) }
    private lateinit var items: List<Item>

    fun setItems(items: List<Item>, defaultItem: Item? = null) {
        this.items = items
        val adapter = ArrayAdapter(context, R.layout.view_dropdown_item, items.map { it.text })
        tvAutoComplete.setAdapter(adapter)
        defaultItem?.let { tvAutoComplete.setText(defaultItem.text) }
    }

    fun getCurrentItem(): Any? {
        val text = tvAutoComplete.text.toString()
        return items.find { it.text == text }
    }
}