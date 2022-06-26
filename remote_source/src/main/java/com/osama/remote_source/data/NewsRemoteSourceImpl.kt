package com.osama.remote_source.data

import com.osama.data.NewsRemoteSource
import com.osama.data.model.NewsRepositoryModel
import com.osama.remote_source.ApiService
import com.osama.remote_source.mapper.NewsResponseToRepositoryModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class NewsRemoteSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val newsResponseToRepositoryModelMapper: NewsResponseToRepositoryModelMapper
) : NewsRemoteSource {

    private val _newsSharedFlow = MutableStateFlow(getInitialStateNewsModels())
    private val newsSharedFlow = _newsSharedFlow.asSharedFlow()

    override suspend fun getNews(): Flow<NewsRepositoryModel> {
        try {
            newsResponseToRepositoryModelMapper.toRepositoryModel(apiService.getNews())
                .let { _newsSharedFlow.emit(it) }
        } catch (connectionException: java.net.UnknownHostException) {
            throw connectionException
        }
        return newsSharedFlow.distinctUntilChanged()
    }

    private fun getInitialStateNewsModels() =
        NewsRepositoryModel(status = "", totalResults = 0, articles = emptyList())
}