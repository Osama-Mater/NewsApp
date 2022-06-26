package com.osama.remote_source.network

import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "33e6be8e36fe4f2a840b042017043ec0")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}