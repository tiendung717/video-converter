package com.alticode.core.data.domain

import com.alticode.core.data.domain.model.Media

interface VideoRepo {

    suspend fun getAllVideos() : List<Media>
    suspend fun saveVideo(media: Media)
    suspend fun deleteVideo(media: Media)
}