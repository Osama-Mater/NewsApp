package com.osama.data

import com.osama.data.model.NewsRepositoryModel
import kotlinx.coroutines.flow.Flow

interface NewsRemoteSource {
    suspend fun getNews(): Flow<NewsRepositoryModel>
}