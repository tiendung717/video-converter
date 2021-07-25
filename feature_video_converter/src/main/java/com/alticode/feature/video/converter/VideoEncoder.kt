package com.alticode.feature.video.converter

import android.media.MediaCodecInfo
import android.media.MediaFormat
import java.io.Serializable

sealed class VideoEncoder(
    val name: String,
    val profile: Int = 0,
    val fileTypes: List<FileType>,
    val mimeType: String
) :
    Serializable {
    object H264Baseline : VideoEncoder(
        "H264 (BASELINE)",
        profile = MediaCodecInfo.CodecProfileLevel.AVCProfileBaseline,
        fileTypes = listOf(
            FileType.MP4,
            FileType.MKV,
            FileType.ThreeGPP,
            FileType.TS
        ),
        mimeType = MediaFormat.MIMETYPE_VIDEO_AVC
    )

    object H264Main : VideoEncoder(
        "H264 (MAIN)",
        profile = MediaCodecInfo.CodecProfileLevel.AVCProfileMain,
        fileTypes = listOf(
            FileType.MP4,
            FileType.MKV,
            FileType.ThreeGPP,
            FileType.TS
        ),
        mimeType = MediaFormat.MIMETYPE_VIDEO_AVC
    )

    object H263 : VideoEncoder(
        "H263",
        fileTypes = listOf(
            FileType.MP4,
            FileType.MKV,
            FileType.ThreeGPP
        ),
        mimeType = MediaFormat.MIMETYPE_VIDEO_H263
    )

    object MPEG4 : VideoEncoder(
        "MPEG4",
        fileTypes = listOf(
            FileType.ThreeGPP
        ),
        mimeType = MediaFormat.MIMETYPE_VIDEO_MPEG4
    )

    object VP8 : VideoEncoder(
        "VP8",
        fileTypes = listOf(
            FileType.MKV,
            FileType.WEBM
        ),
        mimeType = MediaFormat.MIMETYPE_VIDEO_VP8
    )

    object VP9 : VideoEncoder(
        "VP9",
        fileTypes = listOf(
            FileType.MKV,
            FileType.WEBM
        ),
        mimeType = MediaFormat.MIMETYPE_VIDEO_VP9
    )

    companion object {
        const val OMX_GOOGLE_H264 = "OMX.google.h264.encoder"
        const val OMX_GOOGLE_H263 = "OMX.google.h263.encoder"
        const val OMX_GOOGLE_MPEG4 = "OMX.google.mpeg4.encoder"
        const val OMX_GOOGLE_VP8 = "OMX.google.vp8.encoder"
        const val OMX_GOOGLE_VP9 = "OMX.google.vp9.encoder"

        fun get() : List<VideoEncoder> {
            val encoders = mutableListOf<VideoEncoder>()
            EncoderManager.getVideoEncoders().forEach {
                when (it.name) {
                    OMX_GOOGLE_H264 -> {
                        encoders.add(H264Baseline)
                        encoders.add(H264Main)
                    }
                    OMX_GOOGLE_H263 -> {
                        encoders.add(H263)
                    }
                    OMX_GOOGLE_MPEG4 -> {
                        encoders.add(MPEG4)
                    }
                    OMX_GOOGLE_VP8 -> {
                        encoders.add(VP8)
                    }
                    OMX_GOOGLE_VP9 -> {
                        encoders.add(VP9)
                    }
                }
            }
            return encoders
        }
    }
}
