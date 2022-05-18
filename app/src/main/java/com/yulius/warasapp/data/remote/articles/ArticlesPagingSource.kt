package com.yulius.warasapp.data.remote.articles

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.data.model.UserPreference
import kotlinx.coroutines.flow.first
import java.lang.Exception

class ArticlesPagingSource(private val apiService: ApiServiceArticles, private val userPreference: UserPreference): PagingSource<Int, Article>()  {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllNews("covid", position+1, "2a81a09b7fae49ba817399a2fc9cb666")
            Log.d("TAG", "load: ${responseData.articles}")
            LoadResult.Page(
                data = responseData.articles,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if(responseData.articles.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception){
            return LoadResult.Error(exception)
        }
    }
}