package com.goodmood.feature.editor.di

import android.content.Context
import com.goodmood.feature.editor.repository.ToolRepo
import com.goodmood.feature.editor.repository.ToolRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EditorModule {

    @Provides
    @Singleton
    fun provideToolRepo(@ApplicationContext context: Context): ToolRepo = ToolRepoImpl(context)
}