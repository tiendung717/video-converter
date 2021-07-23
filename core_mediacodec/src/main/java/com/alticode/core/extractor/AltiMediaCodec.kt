package com.alticode.core.extractor

import com.alticode.core.extractor.model.MediaCodecParam
import com.alticode.core.extractor.model.MediaInfo

interface AltiMediaCodec {

    suspend fun extractMedia(path: String): MediaInfo
    suspend fun encodeVideo(param: MediaCodecParam)
}