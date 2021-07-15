package com.goodmood.feature.editor.repository.filter

import com.goodmood.core.ffmpeg.FFmpegFilter
import com.goodmood.feature.editor.repository.model.Trim
import com.goodmood.feature.editor.utils.toTime

class TrimFilter(private val trim: Trim) : FFmpegFilter() {
    override fun getParams(): List<String> {
        return listOf(
            "-ss",
            trim.startMs.toTime(),
            "-to",
            trim.endMs.toTime(),
            "-r",
            "25"
        )
    }
}