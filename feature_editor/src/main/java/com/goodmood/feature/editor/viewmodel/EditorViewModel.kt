package com.goodmood.feature.editor.viewmodel

import android.net.Uri
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.goodmood.core.data.domain.VideoRepo
import com.goodmood.core.data.domain.model.Video
import com.goodmood.core.ffmpeg.FFCallback
import com.goodmood.core.ffmpeg.FFmpegExecutor
import com.goodmood.feature.editor.repository.ResourceManager
import com.goodmood.feature.editor.repository.ToolRepo
import com.goodmood.feature.editor.repository.model.*
import com.goodmood.platform.utils.FileUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subjects.PublishSubject
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val toolRepo: ToolRepo,
    private val videoRepo: VideoRepo,
    private val resourceManager: ResourceManager,
    private val ffmpegExecutor: FFmpegExecutor
) : ViewModel() {

    lateinit var inputVideoPath: String
    lateinit var inputVideoUri: Uri
    val exportResult: PublishSubject<ExportResult> = PublishSubject.create()

    fun getFontFile() = resourceManager.fontFile

    fun getStickerFiles() = resourceManager.stickerFiles

    fun updateTool(tool: Tool) = toolRepo.updateTool(tool)

    fun removeTool(tool: Tool) = toolRepo.removeTool(tool)

    fun observeTrimUpdated() =
        toolRepo.observeToolUpdated().filter { it is Trim }.map { it as Trim }

    fun observeTextUpdated() =
        toolRepo.observeToolUpdated().filter { it is Text }.map { it as Text }

    fun observeStickerUpdated() =
        toolRepo.observeToolUpdated().filter { it is Sticker }.map { it as Sticker }

    fun observeTextDeleted() =
        toolRepo.observeToolDeleted().filter { it is Text }.map { it as Text }

    fun observeStickerDeleted() =
        toolRepo.observeToolDeleted().filter { it is Sticker }.map { it as Sticker }

    fun observeToolDeleted() =
        toolRepo.observeToolDeleted()

    fun saveExportedVideo(path: String) =
        videoRepo.saveVideo(Video(path))

    fun clearEditor() {
        toolRepo.clearTools()
    }

    private fun output(): String {
        val dirPath = "${Environment.getExternalStorageDirectory().absolutePath}/goodmood"
        val outputDir = File(dirPath)
        if (!outputDir.exists()) outputDir.mkdir()

        val randomName = UUID.randomUUID().toString().take(10).lowercase()
        return "$dirPath/${randomName}.mp4"
    }

    fun export(activity: AppCompatActivity) {
        val output = output()

        ffmpegExecutor.run(activity, inputVideoPath, output, toolRepo.getFFmpegFilters(), object : FFCallback {
            override fun onProgress(progress: String) {
                exportResult.onNext(ExportResult.InProgress(progress))
            }

            override fun onSuccess() {
                exportResult.onNext(ExportResult.Success(output))
            }

            override fun onFailed() {
                exportResult.onNext(ExportResult.Failed("Export failed!"))
            }
        })
    }
}