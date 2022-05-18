package com.yulius.warasapp.data.model

data class ArticlesResponse (
    val articles: List<Article>,
    val status: String,
    val totalResults: Int,
)

data class Article(
    val id: Int? = null,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val source: Source?,
    val content: String?,
)

data class Source(
    val id: Any? = null,
    val name: String,
)