package com.osama.remote_source.di

import com.osama.data.NewsRemoteSource
import com.osama.remote_source.ApiService
import com.osama.remote_source.data.NewsRemoteSourceImpl
import com.osama.remote_source.mapper.NewsResponseToRepositoryModelMapper
import com.osama.remote_source.mapper.NewsResponseToRepositoryModelMapperImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideNewsRemoteSource(
        apiService: ApiService,
        newsResponseToRepositoryModelMapper: NewsResponseToRepositoryModelMapper
    ): NewsRemoteSource = NewsRemoteSourceImpl(apiService, newsResponseToRepositoryModelMapper)

    @Provides
    @Reusable
    fun provideNewsResponseToRepositoryModelMapper(
    ): NewsResponseToRepositoryModelMapper =
        NewsResponseToRepositoryModelMapperImpl()

    @Provides
    internal fun provideApi(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    internal fun provideRetrofit(
        httpBuilder: OkHttpClient.Builder,
        retrofitBuilder: Retrofit.Builder
    ): Retrofit = retrofitBuilder
        .client(httpBuilder.build())
        .build()
}