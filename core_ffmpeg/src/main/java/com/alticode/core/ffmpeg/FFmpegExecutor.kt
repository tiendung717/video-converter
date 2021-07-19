package com.alticode.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import com.alticode.core.ffmpeg.filter.FFmpegFilter

interface FFmpegExecutor {
    fun run(activity: AppCompatActivity, inputPath: String, outputPath: String, filters: List<FFmpegFilter>, ffCallback: FFCallback)
    fun cancel()
}