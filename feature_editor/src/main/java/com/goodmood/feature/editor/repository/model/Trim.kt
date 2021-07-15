package com.goodmood.feature.editor.repository.model

import com.goodmood.feature.editor.utils.toTime

class Trim(startMs: Long, endMs: Long) : Tool(ToolId.TRIM, startMs, endMs) {

    override fun getFFmpegParams(): List<String> {
        return listOf("-ss", startMs.toTime(), "-to", endMs.toTime(), "-r", "25")
    }
}