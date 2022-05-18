package com.yulius.warasapp.data.remote.articles

import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.data.model.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceArticles {
    @GET("v2/everything")
    fun getNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String,
    ): Call<ArrayList<Article>>

    @GET("v2/everything")
    fun getAllNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String,
    ): ArticlesResponse
}