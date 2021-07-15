package com.goodmood.feature.editor.utils

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