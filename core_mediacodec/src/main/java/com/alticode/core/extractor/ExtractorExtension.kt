package com.alticode.core.extractor

import android.media.MediaExtractor
import android.media.MediaFormat

fun MediaExtractor.getVideoTrackIndex() : Int {
    for (i in 0..trackCount) {
        val mediaFormat = getTrackFormat(i)
        val mime = mediaFormat.getString(MediaFormat.KEY_MIME)
        if (mime?.contains("video/") == true) {
            return i
        }
    }
    return -1
}

fun MediaExtractor.getAudioTrackIndex() : Int {
    for (i in 0..trackCount) {
        val mediaFormat = getTrackFormat(i)
        val mime = mediaFormat.getString(MediaFormat.KEY_MIME)
        if (mime?.contains("audio/") == true) {
            return i
        }
    }
    return -1
}