package com.alticode.feature.video.converter.model

import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.util.Range
import com.alticode.platform.log.AppLog
import java.io.Serializable

sealed class AudioEncoder(
    val name: String,
    val mimeType: String,
    val codecInfo: MediaCodecInfo
) : Serializable {

    private val audioCaps by lazy { codecInfo.getCapabilitiesForType(mimeType).audioCapabilities }

    class AAC(codecInfo: MediaCodecInfo) : AudioEncoder(
        "AAC",
        MediaFormat.MIMETYPE_AUDIO_AAC,
        codecInfo
    )

    class AMR_NB(codecInfo: MediaCodecInfo) : AudioEncoder(
        "AMR_NB",
        MediaFormat.MIMETYPE_AUDIO_AMR_NB,
        codecInfo
    )

    class AMR_WB(codecInfo: MediaCodecInfo) : AudioEncoder(
        "AMR_WB",
        MediaFormat.MIMETYPE_AUDIO_AMR_WB,
        codecInfo
    )

    class FLAC(codecInfo: MediaCodecInfo) : AudioEncoder(
        "FLAC",
        MediaFormat.MIMETYPE_AUDIO_FLAC,
        codecInfo
    )

    companion object {
        private const val AAC = "OMX.google.aac.encoder"
        private const val AMR_NB = "OMX.google.amrnb.encoder"
        private const val AMR_WB = "OMX.google.amrwb.encoder"
        private const val FLAC = "OMX.google.flac.encoder"

        fun get(): List<AudioEncoder> {
            val encoders = mutableListOf<AudioEncoder>()
            EncoderManager.getAudioEncoders().forEach {
                when (it.name) {
                    AAC -> encoders.add(AAC(it))
                    AMR_NB -> encoders.add(AMR_NB(it))
                    AMR_WB -> encoders.add(AMR_WB(it))
                    FLAC -> encoders.add(FLAC(it))
                }
            }
            return encoders
        }
    }

    fun getBitrateSupported(): Range<Int> = audioCaps.bitrateRange
}