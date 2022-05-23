package com.yulius.warasapp.ui.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulius.warasapp.data.model.ArticlesResponse
import com.yulius.warasapp.data.remote.articles.ApiConfigArticles
import com.yulius.warasapp.util.ARTICLE_API_KEY
import com.yulius.warasapp.util.DEFAULT_SORT_BY_ARTICLES
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesViewModel : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val listArticles = MutableLiveData<ArticlesResponse>()

    fun setNews(query: String){
        _isLoading.value = true
        ApiConfigArticles.getApiService().getNews(query,
            DEFAULT_SORT_BY_ARTICLES, ARTICLE_API_KEY).enqueue(object:
            Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("TAG", "onResponse: ${response.message()}")
                    listArticles.postValue(response.body())
                    _isLoading.value = false
                } else {
                    Log.d("TAG", "onResponse: ${response.message()}")
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.d("Failure", "onFailure: ${t.message}")
                _isLoading.value = false
            }

        })
    }
    fun getNews() : LiveData<ArticlesResponse> {
        return listArticles
    }
}