package com.osama.domain.di

import com.osama.domain.NewsRepository
import com.osama.domain.usecase.GetNews
import com.osama.domain.usecase.GetNewsImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Reusable
    fun provideGetNews(
        newsRepository: NewsRepository
    ): GetNews = GetNewsImpl(newsRepository)
}
