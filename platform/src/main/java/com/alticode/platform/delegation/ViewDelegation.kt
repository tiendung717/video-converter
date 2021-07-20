package com.alticode.platform.delegation

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun TextView?.text(): ReadWriteProperty<Any, String> {
    val textView = this
    return object : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return textView?.text?.toString().orEmpty()
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            textView?.text = value
        }
    }
}


fun Button.text(): ReadWriteProperty<Any, String> =
    object : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return text.toString()
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
            text = value
        }
    }

fun TextView.hideIfEmpty(): ReadWriteProperty<Any, String?> =
    object : ReadWriteProperty<Any, String?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String {
            return text.toString()
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
            text = value
            visibility = if(value == null || value.isEmpty()) View.GONE else View.VISIBLE
        }
    }

fun TextView?.textColor(): ReadWriteProperty<Any, Int> {
    val textView = this
    return object : ReadWriteProperty<Any, Int> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Int {
            return textView?.currentTextColor ?: Color.BLACK
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
            textView?.setTextColor(value)
        }
    }
}

fun View.isVisible(keepBounds: Boolean): ReadWriteProperty<Any, Boolean> =
    object : ReadWriteProperty<Any, Boolean> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean =
            visibility == View.VISIBLE

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            visibility = when {
                value -> View.VISIBLE
                keepBounds -> View.INVISIBLE
                else -> View.GONE
            }
        }
    }
