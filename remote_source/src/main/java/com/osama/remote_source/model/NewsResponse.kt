package com.osama.remote_source.model


import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {
    @JsonClass(generateAdapter = true)
    data class Article(
        val source: Source,
        val author: String?,
        val title: String,
        val description: String?,
        val url: String,
        val urlToImage: String?,
        val publishedAt: Date,
        val content: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Source(
            val id: String?,
            val name: String
        )
    }
}