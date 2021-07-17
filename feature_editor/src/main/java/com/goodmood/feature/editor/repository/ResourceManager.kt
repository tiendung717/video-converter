package com.goodmood.feature.editor.repository

import android.content.Context
import com.goodmood.feature.editor.EditorConfig
import com.goodmood.platform.utils.FileUtils

class ResourceManager(private val context: Context) {

    // Default font is "Louis.ttf" in assets
    val fontFile by lazy {
        FileUtils.getFileFromAssets(
            context,
            EditorConfig.DEFAULT_FONT_NAME
        ).absolutePath
    }

    // Default font color is RED
    val fontColor by lazy { EditorConfig.DEFAULT_FONT_COLOR }

    // All stickers is in assets
    val stickerFiles by lazy {
        listOf<String>(
            FileUtils.getFileFromAssets(context, "cool.png").absolutePath,
            FileUtils.getFileFromAssets(context, "fun.png").absolutePath,
            FileUtils.getFileFromAssets(context, "happy.png").absolutePath,
            FileUtils.getFileFromAssets(context, "laughing.png").absolutePath,
            FileUtils.getFileFromAssets(context, "dissapointment.png").absolutePath
        )
    }
}