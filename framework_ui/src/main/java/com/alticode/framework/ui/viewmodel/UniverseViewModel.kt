package com.alticode.framework.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.alticode.core.data.domain.MediaRepo
import com.alticode.core.data.domain.model.Media
import com.alticode.core.ffmpeg.FFmpegExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UniverseViewModel @Inject constructor(
    private val ffmpegExecutor: FFmpegExecutor,
    private val mediaRepo: MediaRepo
) : ViewModel() {

    suspend fun saveOutput(media: Media) = mediaRepo.saveOutput(media)
}