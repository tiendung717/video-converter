package com.alticode.feature.video.converter.model

import java.io.Serializable

sealed class VideoFormat(val name: String) : Serializable {
    object MP4: VideoFormat("MP4")
    object MKV: VideoFormat("MKV")
    object ThreeGPP: VideoFormat("3GPP")
    object TS: VideoFormat("TS")
    object WEBM: VideoFormat("WebM")
    object M4A: VideoFormat("M4A")
    object AAC: VideoFormat("AAC")
    object AMR: VideoFormat("AMR")
    object FLAC: VideoFormat("FLAC")
}
