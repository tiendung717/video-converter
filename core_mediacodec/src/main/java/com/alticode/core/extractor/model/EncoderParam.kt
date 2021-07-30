package com.alticode.core.extractor.model

import android.media.MediaCodecInfo
import android.media.MediaCodecList

data class EncoderParam(
    val videoCodecName: String,
    val videoMimeType: String,
    val width: Int,
    val height: Int,
    val videoBitRate: Int,
    val frameRate: Int,
    val audioCodecName: String,
    val audioMimeType: String,
    val audioChannelCount: Int,
    val audioSampleRate: Int,
    val audioBitRate: Int,
    val copyVideo: Boolean = true,
    val copyAudio: Boolean = true,
    val inputPath: String,
    val outputPath: String
)