package com.yulius.warasapp.ui.profile.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.main.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    private val _listHistory = MutableLiveData<ArrayList<History>>()

    val listData = ArrayList<History>()

    fun setHistory(id_user: Int){
        listData.clear()
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
                            _listHistory.postValue(listData)
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
        return _listHistory
    }
}