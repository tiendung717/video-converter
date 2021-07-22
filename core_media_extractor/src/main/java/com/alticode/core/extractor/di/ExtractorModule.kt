package com.alticode.core.extractor.di

import com.alticode.core.extractor.MediaDecoder
import com.alticode.core.extractor.MediaDecoderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExtractorModule {

    @Provides
    @Singleton
    fun provideMediaDecoder(): MediaDecoder = MediaDecoderImpl()
}