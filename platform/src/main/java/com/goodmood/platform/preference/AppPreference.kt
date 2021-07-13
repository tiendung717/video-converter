package com.goodmood.platform.preference

import android.content.Context
import android.content.SharedPreferences

class AppPreference(sharePrefName: String, context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(sharePrefName, Context.MODE_PRIVATE)

    fun writeBoolean(prefKey: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(prefKey, value).apply()
    }

    fun writeLong(prefKey: String, value: Long) {
        sharedPreferences.edit().putLong(prefKey, value).apply()
    }

    fun writeInt(prefKey: String, value: Int) {
        sharedPreferences.edit().putInt(prefKey, value).apply()
    }

    fun writeString(prefKey: String, value: String) {
        sharedPreferences.edit().putString(prefKey, value).apply()
    }

    fun writeFloat(prefKey: String, value: Float) {
        sharedPreferences.edit().putFloat(prefKey, value).apply()
    }

    fun writeStringSet(prefKey: String, value: Set<String>) {
        sharedPreferences.edit().putStringSet(prefKey, value).apply()
    }

    fun readBoolean(prefKey: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(prefKey, defValue)
    }

    fun readLong(prefKey: String, defValue: Long): Long {
        return sharedPreferences.getLong(prefKey, defValue)
    }

    fun readInt(prefKey: String, defValue: Int): Int {
        return sharedPreferences.getInt(prefKey, defValue)
    }

    fun readString(prefKey: String, defValue: String): String {
        return sharedPreferences.getString(prefKey, defValue) ?: defValue
    }

    fun readFloat(prefKey: String, defValue: Float): Float {
        return sharedPreferences.getFloat(prefKey, defValue) ?: defValue
    }

    fun readStringSet(prefKey: String, defValue: Set<String>): Set<String> {
        return sharedPreferences.getStringSet(prefKey, defValue) ?: defValue
    }
}