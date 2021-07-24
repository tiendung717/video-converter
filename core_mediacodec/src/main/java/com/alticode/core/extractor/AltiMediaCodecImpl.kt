package com.alticode.core.extractor

import android.media.*
import com.alticode.core.extractor.cts.VideoComposer
import com.alticode.core.extractor.model.MediaCodecParam
import com.alticode.core.extractor.model.MediaInfo
import com.alticode.platform.log.AppLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AltiMediaCodecImpl : AltiMediaCodec {
    override suspend fun extractMedia(path: String): MediaInfo {
        AppLog.d("Extract: $path")
        val mediaExtractor = MediaExtractor()
        mediaExtractor.setDataSource(path)

        val videoIndex = mediaExtractor.getVideoTrackIndex()
        val videoFormat = mediaExtractor.getTrackFormat(videoIndex)

        val frameRate = videoFormat.getInteger(MediaFormat.KEY_FRAME_RATE)
        val width = videoFormat.getInteger(MediaFormat.KEY_WIDTH)
        val height = videoFormat.getInteger(MediaFormat.KEY_HEIGHT)
        val videoMime = videoFormat.getString(MediaFormat.KEY_MIME)

        val audioIndex = mediaExtractor.getAudioTrackIndex()
        val audioFormat = mediaExtractor.getTrackFormat(audioIndex)
        val audioMime = audioFormat.getString(MediaFormat.KEY_MIME)

        val mcl = MediaCodecList(MediaCodecList.REGULAR_CODECS)
        val audioDecoder = MediaCodec.createByCodecName(mcl.findDecoderForFormat(audioFormat))
        val videoDecoder = MediaCodec.createByCodecName(mcl.findDecoderForFormat(videoFormat))


        val info = buildString {
            append("\n")
            append("audio mime: $audioMime")
            append("\n")
            append("audio decoder: ${audioDecoder.name}")
            append("\n")
            append("audio profile: ${audioFormat.getInteger(MediaFormat.KEY_PROFILE)}")
            append("\n")
            append("audio channel count: ${audioFormat.getInteger(MediaFormat.KEY_CHANNEL_COUNT)}")
            append("\n")
            append("audio bitrate: ${audioFormat.getInteger(MediaFormat.KEY_BIT_RATE)}")
            append("\n")
            append("audio sample rate (Hz): ${audioFormat.getInteger(MediaFormat.KEY_SAMPLE_RATE)}")
            append("\n")
            append("video mime: $videoMime")
            append("\n")
            append("video decoder: ${videoDecoder.name}")
            append("\n")
            append("video profile: ${videoFormat.getInteger(MediaFormat.KEY_PROFILE)}")
        }


        AppLog.d("Info: $info")
        mediaExtractor.release()
        return MediaInfo(
            frameRate = frameRate,
            width = width,
            height = height,
            mimeType = videoMime
        )
    }

    override suspend fun encodeVideo(param: MediaCodecParam) {
        withContext(Dispatchers.IO) {
            VideoComposer(param).extractDecodeEditEncodeMux()
        }
    }
}