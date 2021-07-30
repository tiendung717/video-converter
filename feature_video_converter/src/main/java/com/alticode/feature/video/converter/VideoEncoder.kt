package com.alticode.feature.video.converter

import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.util.Range
import java.io.Serializable

sealed class VideoEncoder(
    val name: String,
    val codecName: String,
    val profile: Int = 0,
    val fileTypes: List<FileType>,
    val mimeType: String,
    val capabilities: MediaCodecInfo.VideoCapabilities
) :
    Serializable {
    class H264Baseline(codecName: String, capabilities: MediaCodecInfo.VideoCapabilities) :
        VideoEncoder(
            "H264 (BASELINE)",
            codecName,
            profile = MediaCodecInfo.CodecProfileLevel.AVCProfileBaseline,
            fileTypes = listOf(FileType.MP4, FileType.MKV, FileType.ThreeGPP, FileType.TS),
            mimeType = MediaFormat.MIMETYPE_VIDEO_AVC,
            capabilities = capabilities
        )

    class H264Main(codecName: String, capabilities: MediaCodecInfo.VideoCapabilities) : VideoEncoder(
        "H264 (MAIN)",
        codecName,
        profile = MediaCodecInfo.CodecProfileLevel.AVCProfileMain,
        fileTypes = listOf(FileType.MP4, FileType.MKV, FileType.ThreeGPP, FileType.TS),
        mimeType = MediaFormat.MIMETYPE_VIDEO_AVC,
        capabilities = capabilities
    )

    class H263(codecName: String, capabilities: MediaCodecInfo.VideoCapabilities) : VideoEncoder(
        "H263",
        codecName,
        fileTypes = listOf(
            FileType.MP4,
            FileType.MKV,
            FileType.ThreeGPP
        ),
        mimeType = MediaFormat.MIMETYPE_VIDEO_H263,
        capabilities = capabilities
    )

    class MPEG4(codecName: String, capabilities: MediaCodecInfo.VideoCapabilities) : VideoEncoder(
        "MPEG4",
        codecName,
        fileTypes = listOf(FileType.ThreeGPP),
        mimeType = MediaFormat.MIMETYPE_VIDEO_MPEG4,
        capabilities = capabilities
    )

    class VP8(codecName: String, capabilities: MediaCodecInfo.VideoCapabilities) : VideoEncoder(
        "VP8",
        codecName,
        fileTypes = listOf(FileType.MKV, FileType.WEBM),
        mimeType = MediaFormat.MIMETYPE_VIDEO_VP8,
        capabilities = capabilities
    )

    class VP9(codecName: String, capabilities: MediaCodecInfo.VideoCapabilities) : VideoEncoder(
        "VP9",
        codecName,
        fileTypes = listOf(FileType.MKV, FileType.WEBM),
        mimeType = MediaFormat.MIMETYPE_VIDEO_VP9,
        capabilities = capabilities
    )

    companion object {
        const val OMX_GOOGLE_H264 = "OMX.google.h264.encoder"
        const val OMX_GOOGLE_H263 = "OMX.google.h263.encoder"
        const val OMX_GOOGLE_MPEG4 = "OMX.google.mpeg4.encoder"
        const val OMX_GOOGLE_VP8 = "OMX.google.vp8.encoder"
        const val OMX_GOOGLE_VP9 = "OMX.google.vp9.encoder"

        fun get(): List<VideoEncoder> {
            val encoders = mutableListOf<VideoEncoder>()
            EncoderManager.getVideoEncoders().forEach {
                when (it.name) {
                    OMX_GOOGLE_H264 -> {
                        val caps =
                            it.getCapabilitiesForType(MediaFormat.MIMETYPE_VIDEO_AVC).videoCapabilities
                        encoders.add(H264Baseline(it.name, caps))
                        encoders.add(H264Main(it.name, caps))
                    }
                    OMX_GOOGLE_H263 -> {
                        val caps =
                            it.getCapabilitiesForType(MediaFormat.MIMETYPE_VIDEO_H263).videoCapabilities
                        encoders.add(H263(it.name, caps))
                    }
                    OMX_GOOGLE_MPEG4 -> {
                        val caps =
                            it.getCapabilitiesForType(MediaFormat.MIMETYPE_VIDEO_MPEG4).videoCapabilities
                        encoders.add(MPEG4(it.name, caps))
                    }
                    OMX_GOOGLE_VP8 -> {
                        val caps =
                            it.getCapabilitiesForType(MediaFormat.MIMETYPE_VIDEO_VP8).videoCapabilities
                        encoders.add(VP8(it.name, caps))
                    }
                    OMX_GOOGLE_VP9 -> {
                        val caps =
                            it.getCapabilitiesForType(MediaFormat.MIMETYPE_VIDEO_VP9).videoCapabilities
                        encoders.add(VP9(it.name, caps))
                    }
                }
            }
            return encoders
        }
    }

    fun isSupport(format: String): Boolean {
        val fileType = fileTypes.find { it.name.equals(format, ignoreCase = true) }
        return fileType != null
    }

    fun getFrameSupported()  = capabilities.supportedFrameRates

    fun getBitrateSupported() = capabilities.bitrateRange

    fun getHeightSupported() = capabilities.supportedHeights

    fun getSupportedFrameRatesFor(width: Int, height: Int) = capabilities.getSupportedFrameRatesFor(width, height)

    fun getSupportedWidthFor(height: Int) = capabilities.getSupportedWidthsFor(height)

    fun areSizeAndRateSupported(width: Int, height: Int, frameRate: Int) = capabilities.areSizeAndRateSupported(width, height, frameRate.toDouble())
}
