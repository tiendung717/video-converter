package com.alticode.feature.video.converter

import java.io.Serializable

sealed class VideoFormat(val name: String) : Serializable {
    object MP4: VideoFormat("MP4")
    object MKV: VideoFormat("MKV")
    object AVI: VideoFormat("AVI")
    object FLV: VideoFormat("FLV")

    companion object {
        fun get() = listOf(
            MP4,
            MKV,
            AVI,
            FLV
        )
    }
}
