package com.alticode.core.data.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alticode.core.data.domain.MediaRepo
import com.alticode.core.data.domain.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val mediaRepo: MediaRepo) : ViewModel() {

    var videoFlow = MutableStateFlow<List<Media>>(emptyList())

    fun getAllVideos() {
        viewModelScope.launch {
            videoFlow.value = mediaRepo.getAllVideos()
        }
    }

    suspend fun saveVideo(media: Media) = mediaRepo.saveOutput(media)

    suspend fun deleteVideo(media: Media) = mediaRepo.deleteVideo(media)
}