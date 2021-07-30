package com.alticode.feature.video.converter

import android.media.MediaCodecInfo
import android.media.MediaFormat
import java.io.Serializable

sealed class AudioEncoder(
    val name: String,
    val codecName: String,
    val mimeType: String,
    val capabilities: MediaCodecInfo.AudioCapabilities
) : Serializable {
    class AAC(codecName: String, capabilities: MediaCodecInfo.AudioCapabilities) : AudioEncoder(
        "AAC",
        codecName,
        MediaFormat.MIMETYPE_AUDIO_AAC,
        capabilities
    )

    class AMR_NB(codecName: String, capabilities: MediaCodecInfo.AudioCapabilities) : AudioEncoder(
        "AMR_NB",
        codecName,
        MediaFormat.MIMETYPE_AUDIO_AMR_NB,
        capabilities
    )

    class AMR_WB(codecName: String, capabilities: MediaCodecInfo.AudioCapabilities) : AudioEncoder(
        "AMR_WB",
        codecName,
        MediaFormat.MIMETYPE_AUDIO_AMR_WB,
        capabilities
    )

    class FLAC(codecName: String, capabilities: MediaCodecInfo.AudioCapabilities) : AudioEncoder(
        "FLAC",
        codecName,
        MediaFormat.MIMETYPE_AUDIO_FLAC,
        capabilities
    )

    companion object {
        const val OMX_GOOGLE_AAC = "OMX.google.aac.encoder"
        const val OMX_GOOGLE_AMR_NB = "OMX.google.amrnb.encoder"
        const val OMX_GOOGLE_AMR_WB = "OMX.google.amrwb.encoder"
        const val OMX_GOOGLE_FLAC = "OMX.google.flac.encoder"

        fun get(): List<AudioEncoder> {
            val encoders = mutableListOf<AudioEncoder>()
            EncoderManager.getAudioEncoders().forEach {
                when (it.name) {
                    OMX_GOOGLE_AAC -> {
                        val cap = it.getCapabilitiesForType(MediaFormat.MIMETYPE_AUDIO_AAC).audioCapabilities
                        encoders.add(AAC(it.name, cap))
                    }
                    OMX_GOOGLE_AMR_NB -> {
                        val cap = it.getCapabilitiesForType(MediaFormat.MIMETYPE_AUDIO_AMR_NB).audioCapabilities
                        encoders.add(AMR_NB(it.name, cap))
                    }
                    OMX_GOOGLE_AMR_WB -> {
                        val cap = it.getCapabilitiesForType(MediaFormat.MIMETYPE_AUDIO_AMR_WB).audioCapabilities
                        encoders.add(AMR_WB(it.name, cap))
                    }
                    OMX_GOOGLE_FLAC -> {
                        val cap = it.getCapabilitiesForType(MediaFormat.MIMETYPE_AUDIO_FLAC).audioCapabilities
                        encoders.add(FLAC(it.name, cap))
                    }
                }
            }

            return encoders
        }
    }

    fun getBitrateSupported() = capabilities.bitrateRange
}