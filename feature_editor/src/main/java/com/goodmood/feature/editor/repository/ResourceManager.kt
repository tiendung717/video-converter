package com.goodmood.feature.editor.repository

import android.content.Context
import com.goodmood.platform.utils.FileUtils

class ResourceManager(private val context: Context) {

    val fontFile by lazy { FileUtils.getFileFromAssets(context, "louis.ttf").absolutePath }
    val stickerFiles by lazy {
        listOf<String>(
            FileUtils.getFileFromAssets(context, "sticker.png").absolutePath
        )
    }
}