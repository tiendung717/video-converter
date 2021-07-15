package com.goodmood.core.data.domain

import com.goodmood.core.data.datasource.database.VideoDatabase
import com.goodmood.core.data.domain.mapper.VideoMapper
import com.goodmood.core.data.domain.model.Video
import io.reactivex.Completable
import io.reactivex.Observable

class VideoRepoImpl(private val database: VideoDatabase) : VideoRepo {

    override fun getAllVideos(): Observable<List<Video>> {
        return database.daoVideo().get()
            .map {
                it.map { item -> VideoMapper.toDomainModel(item) }
            }
    }

    override fun saveVideo(video: Video): Completable {
        val videoEntity = VideoMapper.toDataModel(video)
        return database.daoVideo().insert(videoEntity)
    }

    override fun deleteVideo(video: Video): Completable {
        val videoEntity = VideoMapper.toDataModel(video)
        return database.daoVideo().delete(videoEntity)
    }
}