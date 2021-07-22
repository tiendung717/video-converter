package com.alticode.feature.video.converter

import java.io.Serializable

sealed class VideoCodec(val name: String) : Serializable {
    object H264_BASELINE : VideoCodec("H264 (BASELINE)")
    object H264_HIGH : VideoCodec("H264 (HIGH)")
    object H264_MAIN : VideoCodec("H264 (MAIN)")
    object MPEG4 : VideoCodec("MPEG4")
    object MPEG2 : VideoCodec("MPEG2")
    object MPEG1 : VideoCodec("MPEG1")

    companion object {
        fun get() = listOf(
            H264_BASELINE,
            H264_HIGH,
            H264_MAIN,
            MPEG4,
            MPEG2,
            MPEG1
        )
    }
}
