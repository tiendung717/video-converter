package com.goodmood.core.ffmpeg

abstract class FFmpegFilter {
    abstract fun getParams(): List<String>
}