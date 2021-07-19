package com.alticode.core.ffmpeg.filter

abstract class FFmpegFilter {
    abstract fun getParams(): List<String>
}