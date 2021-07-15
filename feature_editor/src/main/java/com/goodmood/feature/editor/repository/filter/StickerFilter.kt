package com.goodmood.feature.editor.repository.filter

import com.goodmood.core.ffmpeg.FFmpegFilter
import com.goodmood.feature.editor.repository.model.Sticker

class StickerFilter(private val stickerList: List<Sticker>) : FFmpegFilter() {

    override fun getParams(): List<String> {
        val params = mutableListOf<String>()
        stickerList.forEach {
            params.addAll(
                listOf(
                    "-i",
                    it.path,
                    "-filter_complex",
                    "overlay=${it.posX}:${it.posY}"
                )
            )
        }
        return params
    }
}