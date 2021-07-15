package com.goodmood.feature.editor.repository.model

class Sticker(toolId: Long, startMs: Long, endMs: Long) : Tool(toolId, startMs, endMs) {
    override fun getFFmpegParams(): List<String> {
        return emptyList()
    }
}