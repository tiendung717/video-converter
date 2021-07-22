package com.alticode.core.extractor

import com.alticode.core.extractor.model.MediaInfo

interface MediaExtractor {

    suspend fun extractVideo(path: String): MediaInfo
}