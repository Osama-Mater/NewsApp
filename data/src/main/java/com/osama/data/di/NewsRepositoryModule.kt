package com.osama.data.di

import com.osama.data.NewsRemoteSource
import com.osama.data.mapper.NewsRepositoryToDomainModelMapper
import com.osama.data.mapper.NewsRepositoryToDomainModelMapperImpl
import com.osama.data.repository.NewsRepositoryImpl
import com.osama.domain.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NewsRepositoryModule {
    @Provides
    @Reusable
    fun provideNewsRepository(
        newsRemoteSource: NewsRemoteSource,
        newsRepositoryToDomainModelMapper: NewsRepositoryToDomainModelMapper
    ): NewsRepository = NewsRepositoryImpl(
        newsRemoteSource,
        newsRepositoryToDomainModelMapper
    )

    @Provides
    @Reusable
    fun provideNewsRepositoryToDomainModelMapper(): NewsRepositoryToDomainModelMapper =
        NewsRepositoryToDomainModelMapperImpl()
}
