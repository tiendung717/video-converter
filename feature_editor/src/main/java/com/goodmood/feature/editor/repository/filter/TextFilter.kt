package com.goodmood.feature.editor.repository.filter

import com.goodmood.core.ffmpeg.filter.FFmpegNormalFilter
import com.goodmood.feature.editor.repository.model.Text
import java.lang.StringBuilder

class TextFilter(val textList: List<Text>) : FFmpegNormalFilter() {
    override fun getParams(): List<String> {
        val filter = StringBuilder()
        filter.apply {
            append("[in]")
            textList.forEachIndexed { index, text ->
                append("drawtext=text='${text.text}':fontfile=${text.fontPath}:fontsize=${text.fontSize}:x=${text.posX}:y=${text.posY}",)
                if (index < textList.size - 1) append(",")
            }
            append("[out]")
        }

        return listOf(
            "-vf",
            filter.toString(),
            "-c:a",
            "copy"
        )
    }
}