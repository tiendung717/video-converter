package com.goodmood.core.data.domain

import com.goodmood.core.data.domain.model.Video
import io.reactivex.Completable
import io.reactivex.Observable

interface VideoRepo {

    fun getAllVideos() : Observable<List<Video>>
    fun saveVideo(video: Video): Completable
    fun deleteVideo(video: Video): Completable
}