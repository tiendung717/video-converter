package com.alticode.feature.video.converter

import android.media.MediaCodecInfo
import android.media.MediaCodecList

object EncoderManager {

    fun getVideoEncoders() : List<MediaCodecInfo> {
        val mcl = MediaCodecList(MediaCodecList.REGULAR_CODECS)
        return mcl.codecInfos
            .filter { it.isEncoder }
            .filter { it.supportedTypes.any { type -> type.startsWith("video") } }
            .filter { it.name.startsWith("OMX.google") }
    }

    fun getAudioEncoders() : List<MediaCodecInfo> {
        val mcl = MediaCodecList(MediaCodecList.REGULAR_CODECS)
        return mcl.codecInfos
            .filter { it.isEncoder }
            .filter { it.supportedTypes.any { type -> type.startsWith("audio") } }
            .filter { it.name.startsWith("OMX.google") }
    }
}