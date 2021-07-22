package com.alticode.core.extractor

import android.media.*
import com.alticode.core.extractor.model.MediaInfo
import com.alticode.platform.log.AppLog
import java.lang.StringBuilder

class MediaDecoderImpl : MediaDecoder {
    override suspend fun extractMedia(path: String): MediaInfo {
        val mediaExtractor = MediaExtractor()
        mediaExtractor.setDataSource(path)

        val videoIndex = mediaExtractor.getVideoTrackIndex()
        val videoFormat = mediaExtractor.getTrackFormat(videoIndex)

        val frameRate = videoFormat.getInteger(MediaFormat.KEY_FRAME_RATE)
        val width = videoFormat.getInteger(MediaFormat.KEY_WIDTH)
        val height = videoFormat.getInteger(MediaFormat.KEY_HEIGHT)
        val mimeType = videoFormat.getString(MediaFormat.KEY_MIME)


        val allCodecs = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
        val allEncoders = allCodecs.filter { it.isEncoder }
        val allDecoders = allCodecs.filter { !it.isEncoder }
        allEncoders.forEach {
            AppLog.i("Encoder: ${it.name}")
            val supportTypes = StringBuilder()
            it.supportedTypes.forEach { type ->
                supportTypes.append(type).append(",")
            }
            AppLog.i("Supported types: $supportTypes")
        }
        mediaExtractor.release()
        return MediaInfo()
    }


}