package com.goodmood.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity

interface FFmpegExecutor {
    fun run(activity: AppCompatActivity, inputPath: String, outputPath: String, filters: List<FFmpegFilter>, ffmpegCallback: FFCallback)
}