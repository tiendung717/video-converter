package com.goodmood.core.data.domain

import com.goodmood.core.data.datasource.database.VideoDatabase
import com.goodmood.core.data.domain.mapper.VideoMapper
import com.goodmood.core.data.domain.model.Video
import io.reactivex.Completable
import io.reactivex.Observable
import java.io.File

class VideoRepoImpl(private val database: VideoDatabase) : VideoRepo {

    // Get all edited video and remove the video which isn't existed in file system.
    override fun getAllVideos(): Observable<List<Video>> {
        return database.daoVideo().get()
            .map {
                val videoList = it
                    .map { item -> VideoMapper.toDomainModel(item) }
                    .toMutableList()
                videoList.removeAll { v -> !File(v.path).exists() }
                return@map videoList
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