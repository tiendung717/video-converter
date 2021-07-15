package com.goodmood.core.data.di

import android.content.Context
import com.goodmood.core.data.datasource.database.VideoDatabase
import com.goodmood.core.data.domain.VideoRepo
import com.goodmood.core.data.domain.VideoRepoImpl
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
    fun provideVideoRepo(database: VideoDatabase): VideoRepo = VideoRepoImpl(database)
}