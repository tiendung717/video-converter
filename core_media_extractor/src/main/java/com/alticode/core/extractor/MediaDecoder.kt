package com.alticode.core.extractor

import com.alticode.core.extractor.model.MediaInfo

interface MediaDecoder {

    suspend fun extractMedia(path: String): MediaInfo
}