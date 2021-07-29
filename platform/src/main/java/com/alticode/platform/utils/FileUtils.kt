package com.alticode.platform.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException
import java.util.*

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


    fun getOutputPath(name: String, extension: String): String {
        val dirPath = "${Environment.getExternalStorageDirectory().absolutePath}/converter"
        val outputDir = File(dirPath)
        if (!outputDir.exists()) outputDir.mkdir()
        return "$dirPath/${name}.$extension"
    }
}