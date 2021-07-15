package com.goodmood.core.data.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.goodmood.core.data.domain.VideoRepo
import com.goodmood.core.data.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val videoRepo: VideoRepo) : ViewModel() {

    fun getAllVideos() = videoRepo.getAllVideos()

    fun saveVideo(video: Video) = videoRepo.saveVideo(video)

    fun deleteVideo(video: Video) = videoRepo.deleteVideo(video)
}