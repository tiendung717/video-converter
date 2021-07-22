package com.alticode.core.ffmpeg

object FFStepExtension {

    // Cut video
    fun cutVideo(start: String, end: String): FFStep {
        return object : FFStep() {
            override fun getParams() = listOf(
                "-ss",
                start,
                "-to",
                end
            )
        }
    }
}