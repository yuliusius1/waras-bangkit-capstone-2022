package com.yulius.warasapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.data.remote.articles.ApiServiceArticles
import com.yulius.warasapp.data.remote.articles.ArticlesPagingSource

class ArticlesRepository(private val userPreference: UserPreference, private val apiService: ApiServiceArticles)  {
    fun getStory() : LiveData<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                ArticlesPagingSource(apiService, userPreference)
            }
        ).liveData
    }
}