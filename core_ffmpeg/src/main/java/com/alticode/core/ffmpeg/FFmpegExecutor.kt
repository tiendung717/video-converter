package com.alticode.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity

interface FFmpegExecutor {
    fun run(activity: AppCompatActivity, inputPath: String, outputPath: String, filters: List<FFStep>, ffCallback: FFCallback)
    fun cancel()
}