package com.yulius.warasapp.ui.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.data.model.ArticlesResponse
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.data.remote.articles.ApiConfigArticles
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesViewModel(private val pref: UserPreference) : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val listArticles = MutableLiveData<ArticlesResponse>()


    fun setNews(){
        _isLoading.value = true
        ApiConfigArticles.getApiService().getNews("covid","publishedAt","2a81a09b7fae49ba817399a2fc9cb666").enqueue(object:
            Callback<ArticlesResponse> {
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("Success ", "onResponse: ${response.message()}")
                    listArticles.postValue(response.body())
                } else {
                    Log.d("Followers View model", "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.d("Failure", "onFailure: ${t.message}")
            }

        })
    }

    fun getNews() : LiveData<ArticlesResponse> {
        return listArticles
    }

    private val apiService = ApiConfigArticles.getApiService()

//    val stories :  LiveData<PagingData<Article>> = ArticlesRepository(pref,apiService).getStory().cachedIn(viewModelScope)


}