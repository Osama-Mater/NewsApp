package com.osama.domain.usecase

import com.osama.domain.NewsRepository
import com.osama.domain.model.NewsDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface GetNews {
    suspend fun execute(): Flow<NewsDomainModel>
}

class GetNewsImpl @Inject constructor(private val newsRepository: NewsRepository) : GetNews {
    override suspend fun execute(): Flow<NewsDomainModel> {
        return newsRepository.getNews()
    }
}