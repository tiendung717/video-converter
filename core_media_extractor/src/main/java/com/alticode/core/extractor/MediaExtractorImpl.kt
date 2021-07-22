package com.alticode.core.extractor

import android.media.MediaMetadataRetriever
import com.alticode.core.extractor.model.MediaInfo

class MediaExtractorImpl : MediaExtractor {
    override suspend fun extractVideo(path: String): MediaInfo {
        val retriever = MediaMetadataRetriever()
        val mediaInfo = MediaInfo()
        retriever.setDataSource(path)

        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)
        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)
        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)
        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)

        retriever.release()
        return mediaInfo
    }
}