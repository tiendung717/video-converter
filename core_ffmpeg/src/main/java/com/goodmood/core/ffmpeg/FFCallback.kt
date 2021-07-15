package com.goodmood.core.ffmpeg

interface FFCallback {
    fun onProgress(progress: String)
    fun onSuccess() {}
    fun onFailed() {}
    fun onCancel() {}
}