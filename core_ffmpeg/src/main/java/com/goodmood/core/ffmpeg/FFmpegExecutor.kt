package com.goodmood.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity
import com.goodmood.core.ffmpeg.filter.FFmpegFilter
import com.simform.videooperations.FFmpegCallBack

interface FFmpegExecutor {
    fun run(activity: AppCompatActivity, inputPath: String, outputPath: String, filters: List<FFmpegFilter>, ffCallback: FFCallback)
    fun cancel()
}