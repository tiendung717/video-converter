package com.alticode.feature.video.converter

import android.media.MediaFormat
import java.io.Serializable

sealed class AudioEncoder(val name: String, fileTypes: List<FileType>, val mimeType: String) : Serializable {
    object AAC : AudioEncoder(
        "AAC",
        listOf(FileType.ThreeGPP, FileType.MP4, FileType.M4A, FileType.AAC, FileType.TS),
        MediaFormat.MIMETYPE_AUDIO_AAC
    )

    object AMR_NB: AudioEncoder(
        "AMR_NB",
        listOf(FileType.ThreeGPP, FileType.AMR),
        MediaFormat.MIMETYPE_AUDIO_AMR_NB
    )

    object AMR_WB: AudioEncoder(
        "AMR_WB",
        listOf(FileType.ThreeGPP, FileType.AMR),
        MediaFormat.MIMETYPE_AUDIO_AMR_WB
    )

    object FLAC: AudioEncoder(
        "FLAC",
        listOf(FileType.FLAC, FileType.MP4, FileType.M4A),
        MediaFormat.MIMETYPE_AUDIO_FLAC
    )

    companion object {
        const val OMX_GOOGLE_AAC = "OMX.google.aac.encoder"
        const val OMX_GOOGLE_AMR_NB = "OMX.google.amrnb.encoder"
        const val OMX_GOOGLE_AMR_WB = "OMX.google.amrwb.encoder"
        const val OMX_GOOGLE_FLAC = "OMX.google.flac.encoder"

        fun get() : List<AudioEncoder> {
            val encoders = mutableListOf<AudioEncoder>()
            EncoderManager.getAudioEncoders().forEach {
                when (it.name) {
                    OMX_GOOGLE_AAC -> {
                        encoders.add(AAC)
                    }
                    OMX_GOOGLE_AMR_NB -> {
                        encoders.add(AMR_NB)
                    }
                    OMX_GOOGLE_AMR_WB -> {
                        encoders.add(AMR_WB)
                    }
                    OMX_GOOGLE_FLAC -> {
                        encoders.add(FLAC)
                    }
                }
            }

            return encoders
        }
    }
}