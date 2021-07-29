package com.alticode.platform.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.text.DecimalFormat

fun String.hasGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, this) == PackageManager.PERMISSION_GRANTED
}

fun Long.toTime(): String {
    return if (this <= 0) {
        "00:00:00"
    } else {
        val time = this / 1000
        val hour = time / 3600
        val min = time % 3600 / 60
        val sec = time % 3600 % 60
        "%02d:%02d:%02d".format(hour, min, sec)
    }
}

fun Long.toPrettySize(): String {
    val sizeInKb = (this / 1024f).toDouble()
    val sizeInMb = sizeInKb / 1024f
    val format = DecimalFormat("##.##")
    return when {
        sizeInMb >= 1 -> {
            format.format(sizeInMb) + " MB"
        }
        sizeInKb >= 1 -> {
            format.format(sizeInKb) + " KB"
        }
        else -> {
            "$this B"
        }
    }
}