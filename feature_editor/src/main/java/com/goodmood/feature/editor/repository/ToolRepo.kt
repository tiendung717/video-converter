package com.goodmood.feature.editor.repository

import com.goodmood.feature.editor.repository.model.Tool
import io.reactivex.Observable

interface ToolRepo {
    fun clearTools()
    fun updateTool(tool: Tool)
    fun removeTool(tool: Tool)
    fun getTools(): List<Tool>

    fun observeToolUpdated(): Observable<Tool>
    fun observeToolDeleted(): Observable<Tool>
}