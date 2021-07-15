package com.goodmood.feature.editor.repository.model

class Text(toolId: Long, startMs: Long, endMs: Long) : Tool(toolId, startMs, endMs) {
    override fun getFFmpegParams(): List<String> {
        return emptyList()
    }
}