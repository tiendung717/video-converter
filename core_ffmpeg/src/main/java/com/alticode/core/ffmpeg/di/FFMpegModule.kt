package com.alticode.core.ffmpeg.di

import com.alticode.core.ffmpeg.FFMpegExecutorImpl
import com.alticode.core.ffmpeg.FFmpegExecutor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FFMpegModule {

    @Provides
    @Singleton
    fun provideFFMpegExecutor(): FFmpegExecutor = FFMpegExecutorImpl()
}