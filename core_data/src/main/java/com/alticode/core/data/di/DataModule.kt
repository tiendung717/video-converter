package com.alticode.core.data.di

import android.content.Context
import com.alticode.core.data.datasource.database.VideoDatabase
import com.alticode.core.data.domain.MediaRepo
import com.alticode.core.data.domain.MediaRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideVideoDatabase(@ApplicationContext context: Context): VideoDatabase = VideoDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideVideoRepo(database: VideoDatabase): MediaRepo = MediaRepoImpl(database)
}