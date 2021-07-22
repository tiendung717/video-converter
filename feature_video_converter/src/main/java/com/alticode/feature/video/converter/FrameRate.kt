package com.alticode.feature.video.converter

import java.io.Serializable

sealed class FrameRate(val name: String) : Serializable {
    object FPS15 : FrameRate("15")
    object FPS20 : FrameRate("20")
    object FPS24 : FrameRate("24")
    object FPS25 : FrameRate("25")
    object FPS30 : FrameRate("30")
    object FPS50 : FrameRate("50")
    object FPS60 : FrameRate("60")
    object FPS72 : FrameRate("72")
    object FPS120 : FrameRate("120")

    companion object {
        fun get() = listOf(
            FPS15,
            FPS20,
            FPS24,
            FPS25,
            FPS30,
            FPS50,
            FPS60,
            FPS72,
            FPS120
        )
    }
}
