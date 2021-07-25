package com.alticode.core.extractor

import com.alticode.core.extractor.model.MediaEncoderParam
import com.alticode.core.extractor.model.MediaInfo

interface AltiMediaCodec {

    fun extractMedia(path: String): MediaInfo
    suspend fun encodeVideo(param: MediaEncoderParam)
}