package com.osama.remote_source.mapper

import com.osama.data.model.NewsRepositoryModel
import com.osama.remote_source.model.NewsResponse
import javax.inject.Inject

interface NewsResponseToRepositoryModelMapper {
    fun toRepositoryModel(newsResponse: NewsResponse): NewsRepositoryModel
}

class NewsResponseToRepositoryModelMapperImpl @Inject constructor() :
    NewsResponseToRepositoryModelMapper {
    override fun toRepositoryModel(newsResponse: NewsResponse): NewsRepositoryModel {
        return NewsRepositoryModel(
            status = newsResponse.status,
            totalResults = newsResponse.totalResults,
            articles = newsResponse.articles.map { article: NewsResponse.Article ->
                NewsRepositoryModel.Article(
                    source = NewsRepositoryModel.Article.Source(
                        id = article.source.id,
                        name = article.source.name
                    ),
                    author = article.author,
                    title = article.title,
                    description = article.description,
                    url = article.url,
                    urlToImage = article.urlToImage,
                    publishedAt = article.publishedAt,
                    content = article.content
                )
            }
        )
    }
}