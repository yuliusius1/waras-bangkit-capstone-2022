package com.yulius.warasapp.data.model

data class ArticlesResponse (
    val articles: ArrayList<Article>? = null,
    val status: String? = null,
    val totalResult : Int? = null
)


data class Article(
    val source: Source?= null,
    val author: String? = null,
    val title: String? = null,
    val description: String?= null,
    val url: String? = null,
    val urlToImage: String?= null,
    val publishedAt: String?= null,
    val content: String?= null,
)

data class Source(
    val id: Any? = null,
    val name: String? = null,
)