package com.osama.domain

import com.osama.domain.model.NewsDomainModel
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(): Flow<NewsDomainModel>
}