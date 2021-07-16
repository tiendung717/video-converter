package com.goodmood.core.ffmpeg.filter

abstract class FFmpegFilter {
    abstract fun getParams(): List<String>
}