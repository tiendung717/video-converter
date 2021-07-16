package com.goodmood.feature.editor.repository

import android.content.Context
import com.goodmood.core.ffmpeg.filter.FFmpegFilter
import com.goodmood.feature.editor.repository.filter.StickerFilter
import com.goodmood.feature.editor.repository.filter.TextFilter
import com.goodmood.feature.editor.repository.filter.TrimFilter
import com.goodmood.feature.editor.repository.model.Sticker
import com.goodmood.feature.editor.repository.model.Text
import com.goodmood.feature.editor.repository.model.Tool
import com.goodmood.feature.editor.repository.model.Trim
import com.goodmood.platform.utils.FileUtils
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ToolRepoImpl(private val context: Context) : ToolRepo {

    private val tools = mutableMapOf<Long, Tool>()
    private val toolUpdater: PublishSubject<Tool> = PublishSubject.create()
    private val toolRemover: PublishSubject<Tool> = PublishSubject.create()
    private val fontPath: String by lazy {
        FileUtils.getFileFromAssets(context, "louis.ttf").absolutePath
    }

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

    override fun getFontFile(): String {
        return fontPath
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

    override fun getFFmpegFilters(): List<FFmpegFilter> {
        val trim = tools.values.find { it is Trim }
        val texts = tools.values.filterIsInstance<Text>()
        val stickers = tools.values.filterIsInstance<Sticker>()

        return mutableListOf<FFmpegFilter>().apply {
            if (stickers.isNotEmpty()) {
                add(StickerFilter(stickers))
            }

            if (trim != null) {
                add(TrimFilter(trim as Trim))
            }

            if (texts.isNotEmpty()) {
                add(TextFilter(texts))
            }
        }
    }
}