package com.goodmood.feature.editor.di

import com.goodmood.feature.editor.repository.ToolRepo
import com.goodmood.feature.editor.repository.ToolRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EditorModule {

    @Provides
    @Singleton
    fun provideToolRepo(): ToolRepo = ToolRepoImpl()
}