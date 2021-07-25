package com.alticode.feature.video.converter

import java.io.Serializable

sealed class FileType(val name: String) : Serializable {
    object MP4: FileType("MP4")
    object MKV: FileType("MKV")
    object ThreeGPP: FileType("3GPP")
    object TS: FileType("TS")
    object WEBM: FileType("WebM")
    object M4A: FileType("M4A")
    object AAC: FileType("AAC")
    object AMR: FileType("AMR")
    object FLAC: FileType("FLAC")
}
