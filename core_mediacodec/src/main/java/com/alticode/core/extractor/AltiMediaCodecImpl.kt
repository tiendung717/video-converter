package com.alticode.core.extractor

import android.media.*
import com.alticode.core.extractor.cts.VideoComposer
import com.alticode.core.extractor.model.MediaEncoderParam
import com.alticode.core.extractor.model.MediaInfo
import com.alticode.platform.log.AppLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AltiMediaCodecImpl : AltiMediaCodec {
    override fun extractMedia(path: String): MediaInfo {
        val mediaExtractor = MediaExtractor()
        mediaExtractor.setDataSource(path)

        val videoIndex = mediaExtractor.getVideoTrackIndex()
        val videoFormat = mediaExtractor.getTrackFormat(videoIndex)
        val width = videoFormat.getInteger(MediaFormat.KEY_WIDTH)
        val height = videoFormat.getInteger(MediaFormat.KEY_HEIGHT)

        val audioIndex = mediaExtractor.getAudioTrackIndex()
        val audioFormat = mediaExtractor.getTrackFormat(audioIndex)
        val audioSampleRate = audioFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)
        val audioChannelCount = audioFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)

        AppLog.d("Origin $width x $height")
        mediaExtractor.release()
        return MediaInfo(
            width = width,
            height = height,
            audioChannelCount = audioChannelCount,
            audioSampleRate = audioSampleRate
        )
    }

    override suspend fun encodeVideo(param: MediaEncoderParam) {
        withContext(Dispatchers.IO) {
            VideoComposer(param).extractDecodeEditEncodeMux()
        }
    }
}