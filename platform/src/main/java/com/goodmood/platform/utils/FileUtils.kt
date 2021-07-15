package com.goodmood.platform.utils

import android.content.Context
import java.io.File
import java.io.IOException

object FileUtils {
    @Throws(IOException::class)
    fun getFileFromAssets(context: Context, fileName: String): File =
        File(context.cacheDir, fileName).also {
            if (!it.exists()) {
                it.outputStream().use { cache ->
                    context.assets.open(fileName).use { inputStream ->
                        inputStream.copyTo(cache)
                    }
                }
            }
        }
}