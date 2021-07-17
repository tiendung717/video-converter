package com.goodmood.feature.editor.repository.filter

import com.goodmood.core.ffmpeg.filter.FFmpegMergeFilter
import com.goodmood.feature.editor.repository.model.Sticker
import com.goodmood.platform.log.AppLog
import java.lang.StringBuilder

class StickerFilter(private val stickerList: List<Sticker>) : FFmpegMergeFilter() {

    override fun getParams(): List<String> {
        val params = mutableListOf<String>()
        stickerList.forEach {
            params.addAll(
                listOf(
                    "-i",
                    it.path
                )
            )
        }

        params.add("-filter_complex")
        if (stickerList.size == 1) {
            val sticker = stickerList[0]
            params.add("overlay=${sticker.xPercent}*W:${sticker.yPercent}*H")
        } else {
            val filterExpr = StringBuilder()
            stickerList.forEachIndexed { index, sticker ->
                when (index) {
                    0 -> {
                        filterExpr.append("[0][${index + 1}]")
                        filterExpr.append("overlay=${sticker.xPercent}*W:${sticker.yPercent}*H")
                        filterExpr.append("[label${index}]")
                        filterExpr.append(";")
                    }
                    stickerList.size - 1 -> {
                        filterExpr.append("[label${index - 1}][${index + 1}]")
                        filterExpr.append("overlay=${sticker.xPercent}*W:${sticker.yPercent}*H")
                    }
                    else -> {
                        filterExpr.append("[label${index - 1}][${index + 1}]")
                        filterExpr.append("overlay=${sticker.xPercent}*W:${sticker.yPercent}*H")
                        filterExpr.append("[label${index}]")
                        filterExpr.append(";")
                    }
                }
            }
            params.add(filterExpr.toString())
        }

        return params
    }
}