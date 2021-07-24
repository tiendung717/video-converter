package com.alticode.core.extractor.model

import android.media.MediaCodecInfo
import android.media.MediaCodecList

class MediaCodecParam(
    val videoMimeType: String, // MediaFormat.MIMETYPE_VIDEO_AVC
    val audioMimeType: String,
    val audioChannelCount: Int,
    val audioSampleRate: Int,
    val width: Int,
    val height: Int,
    val bitRate: Int,
    val frameRate: Int,
    val copyVideo: Boolean,
    val copyAudio: Boolean,
    val inputPath: String,
    val outputPath: String
) {

    fun getCodecInfo(): MediaCodecInfo? {
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)
        codecList.codecInfos.filter { it.isEncoder }.forEach {
            if (it.supportedTypes.find { type -> type.equals(videoMimeType, true) } != null) {
                return it
            }
        }
        return null
    }
}