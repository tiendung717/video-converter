package com.alticode.feature.video.converter.model

import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.util.Range
import com.alticode.platform.log.AppLog
import java.io.Serializable

sealed class VideoEncoder(
    val name: String,
    val videoFormats: List<VideoFormat>,
    val mimeType: String,
    val codecInfo: MediaCodecInfo
) : Serializable {
    private val videoCaps by lazy { codecInfo.getCapabilitiesForType(mimeType).videoCapabilities }

    class H264(codecInfo: MediaCodecInfo) :
        VideoEncoder(
            "H.264",
            listOf(VideoFormat.MP4, VideoFormat.MKV, VideoFormat.ThreeGPP, VideoFormat.TS),
            MediaFormat.MIMETYPE_VIDEO_AVC,
            codecInfo
        )

    class H263(codecInfo: MediaCodecInfo) : VideoEncoder(
        "H.263",
        listOf(VideoFormat.MP4, VideoFormat.MKV, VideoFormat.ThreeGPP),
        MediaFormat.MIMETYPE_VIDEO_H263,
        codecInfo
    )

    class MPEG4(codecInfo: MediaCodecInfo) : VideoEncoder(
        "MPEG4",
        listOf(VideoFormat.ThreeGPP),
        MediaFormat.MIMETYPE_VIDEO_MPEG4,
        codecInfo
    )

    class VP8(codecInfo: MediaCodecInfo) : VideoEncoder(
        "VP8",
        listOf(VideoFormat.MKV, VideoFormat.WEBM),
        MediaFormat.MIMETYPE_VIDEO_VP8,
        codecInfo
    )

    class HEVC(codecInfo: MediaCodecInfo) : VideoEncoder(
        "HEVC (H.265)",
        listOf(VideoFormat.MKV, VideoFormat.MP4),
        MediaFormat.MIMETYPE_VIDEO_HEVC,
        codecInfo
    )

    companion object {
        private const val H264 = "OMX.qcom.video.encoder.avc"
        private const val H263 = "OMX.qcom.video.encoder.h263sw"
        private const val MPEG4 = "OMX.qcom.video.encoder.mpeg4sw"
        private const val VP8 = "OMX.qcom.video.encoder.vp8"
        private const val HEVC = "OMX.qcom.video.encoder.hevc"

        fun get(): List<VideoEncoder> {
            val encoders = mutableListOf<VideoEncoder>()
            EncoderManager.getVideoEncoders().forEach {
                when (it.name) {
                    H264 -> encoders.add(H264(it))
                    H263 -> encoders.add(H263(it))
                    MPEG4 -> encoders.add(MPEG4(it))
                    VP8 -> encoders.add(VP8(it))
                    HEVC -> encoders.add(HEVC(it))
                }
            }
            return encoders
        }
    }

    fun isSupport(format: String): Boolean {
        val fileType = videoFormats.find { it.name.equals(format, ignoreCase = true) }
        return fileType != null
    }

    fun getBitrateSupported(): Range<Int> = videoCaps.bitrateRange

    fun getHeightSupported(): Range<Int> = videoCaps.supportedHeights

    fun getSupportedFrameRatesFor(width: Int, height: Int): Range<Double> =
        videoCaps.getSupportedFrameRatesFor(width, height)

    fun getSupportedWidthFor(height: Int): Range<Int> = videoCaps.getSupportedWidthsFor(height)

    fun areSizeAndRateSupported(width: Int, height: Int, frameRate: Int) =
        videoCaps.areSizeAndRateSupported(width, height, frameRate.toDouble())
}
