package com.goodmood.feature.editor.repository

import com.goodmood.core.ffmpeg.filter.FFmpegFilter
import com.goodmood.feature.editor.repository.model.Tool
import io.reactivex.Observable

interface ToolRepo {
    fun getFontFile(): String
    fun clearTools()
    fun updateTool(tool: Tool)
    fun removeTool(tool: Tool)
    fun getTools(): List<Tool>
    fun getFFmpegFilters(): List<FFmpegFilter>

    fun observeToolUpdated(): Observable<Tool>
    fun observeToolDeleted(): Observable<Tool>
}