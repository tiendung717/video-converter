package com.goodmood.feature.editor.repository.model

abstract class Tool(var toolId: Long, var startMs: Long, var endMs: Long) {

    abstract fun getFFmpegParams() : List<String>
}