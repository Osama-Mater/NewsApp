package com.osama.data.repository

import com.osama.data.NewsRemoteSource
import com.osama.data.mapper.NewsRepositoryToDomainModelMapper
import com.osama.domain.NewsRepository
import com.osama.domain.model.NewsDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsRemoteSource: NewsRemoteSource,
    private val newsRepositoryToDomainModelMapper: NewsRepositoryToDomainModelMapper
) : NewsRepository {
    override suspend fun getNews(): Flow<NewsDomainModel> =
        newsRemoteSource.getNews().map { newsRepositoryToDomainModelMapper.toDomainModel(it) }
}