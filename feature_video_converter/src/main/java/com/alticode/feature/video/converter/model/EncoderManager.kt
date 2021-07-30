package com.alticode.feature.video.converter.model

import android.media.MediaCodecInfo
import android.media.MediaCodecList

object EncoderManager {

    fun getVideoEncoders() : List<MediaCodecInfo> {
        val mcl = MediaCodecList(MediaCodecList.REGULAR_CODECS)
        return mcl.codecInfos
            .filter { it.isEncoder }
            .filter { it.supportedTypes.any { type -> type.startsWith("video") } }
    }

    fun getAudioEncoders() : List<MediaCodecInfo> {
        val mcl = MediaCodecList(MediaCodecList.REGULAR_CODECS)
        return mcl.codecInfos
            .filter { it.isEncoder }
            .filter { it.supportedTypes.any { type -> type.startsWith("audio") } }
    }
}