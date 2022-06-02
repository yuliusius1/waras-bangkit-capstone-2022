package com.yulius.warasapp.ui.home

import androidx.lifecycle.*
import com.yulius.warasapp.data.model.ResponseQuotes
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.data.remote.quotes.ApiConfigQuotes
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: UserPreference) : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val listQuotes = MutableLiveData<ResponseQuotes>()


    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun setQuote(randomInt: Int){
        _isLoading.value = true
        ApiConfigQuotes.getApiService().getQuotes("health",1,randomInt
        ).enqueue(object:
            Callback<ResponseQuotes> {
            override fun onResponse(
                call: Call<ResponseQuotes>,
                response: Response<ResponseQuotes>
            ) {
                if(response.isSuccessful){
                    listQuotes.postValue(response.body())
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseQuotes>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun getQuotes() : LiveData<ResponseQuotes> {
        return listQuotes
    }
}