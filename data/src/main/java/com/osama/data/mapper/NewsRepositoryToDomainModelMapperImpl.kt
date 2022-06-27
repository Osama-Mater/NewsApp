package com.osama.data.mapper

import com.osama.data.model.NewsRepositoryModel
import com.osama.domain.model.NewsDomainModel
import javax.inject.Inject

interface NewsRepositoryToDomainModelMapper {
    fun toDomainModel(newsRepositoryModel: NewsRepositoryModel): NewsDomainModel
}

class NewsRepositoryToDomainModelMapperImpl @Inject constructor() :
    NewsRepositoryToDomainModelMapper {
    override fun toDomainModel(newsRepositoryModel: NewsRepositoryModel): NewsDomainModel {
        return NewsDomainModel(
            status = newsRepositoryModel.status,
            totalResults = newsRepositoryModel.totalResults,
            articles = newsRepositoryModel.articles.map { article: NewsRepositoryModel.Article ->
                NewsDomainModel.Article(
                    source = NewsDomainModel.Article.Source(
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