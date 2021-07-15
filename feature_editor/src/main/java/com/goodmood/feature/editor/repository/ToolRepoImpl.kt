package com.goodmood.feature.editor.repository

import com.goodmood.feature.editor.repository.model.Tool
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ToolRepoImpl : ToolRepo {

    private val tools = mutableMapOf<Long, Tool>()
    private val toolUpdater: PublishSubject<Tool> = PublishSubject.create()
    private val toolRemover: PublishSubject<Tool> = PublishSubject.create()

    override fun updateTool(tool: Tool) {
        tools[tool.toolId] = tool
        toolUpdater.onNext(tool)
    }

    override fun removeTool(tool: Tool) {
        val removedTool = tools.remove(tool.toolId)
        removedTool?.let { toolRemover.onNext(it) }
    }

    override fun observeToolUpdated(): Observable<Tool> {
        return toolUpdater
    }

    override fun observeToolDeleted(): Observable<Tool> {
        return toolRemover
    }

    override fun getTools(): List<Tool> {
        return tools.values.toList()
    }

    override fun clearTools() {
        tools.clear()
    }
}