package com.goodmood.platform.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun String.hasGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, this) == PackageManager.PERMISSION_GRANTED
}