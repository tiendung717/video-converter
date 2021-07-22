package com.alticode.feature.video.converter

import java.io.Serializable

sealed class AudioCodec(val name: String) : Serializable {
    object AAC : AudioCodec("AAC")
    object MP3 : AudioCodec("MP3")
    object MP2 : AudioCodec("MP2")

    companion object {
        fun get() = listOf(
            AAC,
            MP3,
            MP2
        )
    }
}