package com.yulius.warasapp.ui.profile.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.articles.ApiConfigArticles
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.util.ARTICLE_API_KEY
import com.yulius.warasapp.util.DEFAULT_SORT_BY_ARTICLES
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    val listHistory = MutableLiveData<ArrayList<History>>()
    val listData = ArrayList<History>()

    fun setHistory(id_user: Int){
        _isLoading.value = true
        ApiConfig.getApiService().getAllHistory(
        ).enqueue(object:
            Callback<ResponseAllHistory> {
            override fun onResponse(
                call: Call<ResponseAllHistory>,
                response: Response<ResponseAllHistory>
            ) {
                val responseBody= response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success"){
                        if(responseBody.data != null) {
                            for (i in responseBody.data.indices){
                                if (responseBody.data[i].id_user == id_user) {
                                    listData.add(responseBody.data[i])
                                }
                            }
                            listHistory.postValue(listData)
                        }
                    }
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseAllHistory>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }
    fun getHistory() : LiveData<ArrayList<History>> {
        return listHistory
    }
}