package com.goodmood.platform.delegation

import com.goodmood.platform.preference.AppPreference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun AppPreference.string(
    defaultValue: String = "",
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, String> =
    object : ReadWriteProperty<Any, String> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = readString(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: String
        ) = writeString(key(property), value)
    }

fun AppPreference.int(
    defaultValue: Int = 0,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, Int> =
    object : ReadWriteProperty<Any, Int> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = readInt(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Int
        ) = writeInt(key(property), value)
    }

fun AppPreference.float(
    defaultValue: Float = 0f,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, Float> =
    object : ReadWriteProperty<Any, Float> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = readFloat(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Float
        ) = writeFloat(key(property), value)
    }

fun AppPreference.long(
    defaultValue: Long = 0L,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, Long> =
    object : ReadWriteProperty<Any, Long> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = readLong(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Long
        ) = writeLong(key(property), value)
    }

fun AppPreference.boolean(
    defaultValue: Boolean = false,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, Boolean> =
    object : ReadWriteProperty<Any, Boolean> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = readBoolean(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Boolean
        ) = writeBoolean(key(property), value)
    }
