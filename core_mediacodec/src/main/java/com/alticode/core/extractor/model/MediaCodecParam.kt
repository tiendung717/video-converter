package com.alticode.core.extractor.model

import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.media.MediaFormat

class MediaCodecParam(
    val mimeType: String, // MediaFormat.MIMETYPE_VIDEO_AVC
    val width: Int,
    val height: Int,
    val bitRate: Int,
    val frameRate: Int,
    val numFrame: Int,
    val copyVideo: Boolean,
    val copyAudio: Boolean,
    val inputPath: String,
    val outputPath: String
) {

    fun getCodecInfo(): MediaCodecInfo? {
        val codecList = MediaCodecList(MediaCodecList.ALL_CODECS)
        codecList.codecInfos.filter { it.isEncoder }.forEach {
            if (it.supportedTypes.find { type -> type.equals(mimeType, true) } != null) {
                return it
            }
        }
        return null
    }
}