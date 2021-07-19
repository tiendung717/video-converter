package com.alticode.core.data.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alticode.core.data.domain.VideoRepo
import com.alticode.core.data.domain.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val videoRepo: VideoRepo) : ViewModel() {

    var videoFlow = MutableStateFlow<List<Media>>(emptyList())

    fun getAllVideos() {
        viewModelScope.launch {
            videoFlow.value = videoRepo.getAllVideos()
        }
    }

    suspend fun saveVideo(media: Media) = videoRepo.saveVideo(media)

    suspend fun deleteVideo(media: Media) = videoRepo.deleteVideo(media)
}