package com.alticode.core.data.domain

import com.alticode.core.data.datasource.database.VideoDatabase
import com.alticode.core.data.domain.mapper.VideoMapper
import com.alticode.core.data.domain.model.Media
import java.io.File

class VideoRepoImpl(private val database: VideoDatabase) : VideoRepo {

    override suspend fun getAllVideos(): List<Media> {
        return database.daoVideo().get()
                .map { VideoMapper.toDomainModel(it) }
                .filter { File(it.path).exists() }
    }

    override suspend fun saveVideo(media: Media) {
        val videoEntity = VideoMapper.toDataModel(media)
        return database.daoVideo().insert(videoEntity)
    }

    override suspend fun deleteVideo(media: Media) {
        val videoEntity = VideoMapper.toDataModel(media)
        return database.daoVideo().delete(videoEntity)
    }
}