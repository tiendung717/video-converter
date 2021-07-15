package com.goodmood.core.ffmpeg

import androidx.appcompat.app.AppCompatActivity

interface FFmpegExecutor {
    fun run(activity: AppCompatActivity, command: Array<String>, ffmpegCallback: FFCallback)
}