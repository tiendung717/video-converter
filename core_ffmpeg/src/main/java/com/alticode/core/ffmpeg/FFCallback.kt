package com.alticode.core.ffmpeg

interface FFCallback {
    fun onProgress(progress: String)
    fun onSuccess() {}
    fun onFailed() {}
    fun onCancel() {}
}