package com.osama.remote_source

import com.osama.remote_source.model.NewsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("v2/everything?domains=wsj.com")
    suspend fun getNews(): NewsResponse
}
