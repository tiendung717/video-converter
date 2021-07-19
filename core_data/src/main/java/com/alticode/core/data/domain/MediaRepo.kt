package com.alticode.core.data.domain

import com.alticode.core.data.domain.model.Media

interface MediaRepo {

    suspend fun getAllVideos() : List<Media>
    suspend fun saveOutput(media: Media)
    suspend fun deleteVideo(media: Media)
}