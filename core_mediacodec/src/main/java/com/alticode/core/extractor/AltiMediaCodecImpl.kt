package com.alticode.core.extractor

import android.media.*
import com.alticode.core.extractor.cts.VideoComposer
import com.alticode.core.extractor.model.MediaCodecParam
import com.alticode.core.extractor.model.MediaInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AltiMediaCodecImpl : AltiMediaCodec {
    override suspend fun extractMedia(path: String): MediaInfo {
        val mediaExtractor = MediaExtractor()
        mediaExtractor.setDataSource(path)

        val videoIndex = mediaExtractor.getVideoTrackIndex()
        val videoFormat = mediaExtractor.getTrackFormat(videoIndex)

        val frameRate = videoFormat.getInteger(MediaFormat.KEY_FRAME_RATE)
        val width = videoFormat.getInteger(MediaFormat.KEY_WIDTH)
        val height = videoFormat.getInteger(MediaFormat.KEY_HEIGHT)
        val mimeType = videoFormat.getString(MediaFormat.KEY_MIME)

        mediaExtractor.release()
        return MediaInfo(
            frameRate = frameRate,
            width = width,
            height = height,
            mimeType = mimeType
        )
    }

    override suspend fun encodeVideo(param: MediaCodecParam) {
        withContext(Dispatchers.IO) {
            VideoComposer(param).extractDecodeEditEncodeMux()
        }
    }
}