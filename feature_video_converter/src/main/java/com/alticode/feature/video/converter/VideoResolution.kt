package com.alticode.feature.video.converter

import java.io.Serializable

sealed class VideoResolution(val name: String) : Serializable {
    object R1080P : VideoResolution("1080P")
    object R960P : VideoResolution("960P")
    object R720P : VideoResolution("720P")
    object R640P : VideoResolution("640P")
    object R480P : VideoResolution("480P")
    object R360P : VideoResolution("360P")
    object R320P : VideoResolution("320P")
    object R240P : VideoResolution("240P")
    object R144P : VideoResolution("144P")

    companion object {
        fun get() = listOf(
            R1080P,
            R960P,
            R720P,
            R640P,
            R480P,
            R360P,
            R320P,
            R240P,
            R144P
        )
    }
}
