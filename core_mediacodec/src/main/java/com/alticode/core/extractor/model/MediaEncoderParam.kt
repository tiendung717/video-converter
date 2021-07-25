package com.alticode.core.extractor.model

import android.media.MediaCodecInfo
import android.media.MediaCodecList

data class MediaEncoderParam(
    val videoMimeType: String,
    val videoEncoderProfile: Int,
    val width: Int,
    val height: Int,
    val videoBitRate: Int,
    val frameRate: Int,
    val audioMimeType: String,
    val audioChannelCount: Int,
    val audioSampleRate: Int,
    val audioBitRate: Int,
    val copyVideo: Boolean = true,
    val copyAudio: Boolean = true,
    val inputPath: String,
    val outputPath: String
)