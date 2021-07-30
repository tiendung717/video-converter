package com.alticode.platform.delegation

import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.slider.Slider
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

fun Slider.valueFrom(): ReadWriteProperty<Any, Float> {
    return object : ReadWriteProperty<Any, Float> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Float {
            return valueFrom
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
            valueFrom = value
        }
    }
}

fun Slider.valueTo(): ReadWriteProperty<Any, Float> {
    return object : ReadWriteProperty<Any, Float> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Float {
            return valueTo
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
            valueTo = value
        }
    }
}

fun Slider.value(): ReadWriteProperty<Any, Float> {
    return object : ReadWriteProperty<Any, Float> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Float {
            return value
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, fValue: Float) {
            value = fValue
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
