package com.alticode.feature.video.converter

import java.io.Serializable

sealed class VideoResolution(val name: String, val value: Int) : Serializable {
    object R1080P : VideoResolution("1080P", 1072)
    object R960P : VideoResolution("960P", 960)
    object R720P : VideoResolution("720P", 720)
    object R640P : VideoResolution("640P", 640)
    object R480P : VideoResolution("480P", 480)
    object R360P : VideoResolution("360P", 352)
    object R320P : VideoResolution("320P", 320)
    object R240P : VideoResolution("240P", 240)
    object R144P : VideoResolution("144P", 144)

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

    fun getWidth(orgWidth: Int, orgHeight: Int): Int {
        val width = orgWidth * value / orgHeight
        return (width / 16) * 16
    }
}
