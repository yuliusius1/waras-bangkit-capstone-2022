package com.yulius.warasapp.ui.auth.check_email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.util.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckEmailViewModel : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val user = MutableLiveData<UserLogin>()

    fun checkEmail(email: String, callback: ResponseCallback){
        _isLoading.value = true
        ApiConfig.getApiService().checkEmail(email).enqueue(object:
            Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success" && responseBody.data != null){
                        user.value = responseBody.data!!

                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                        } else {
                            callback.getCallback("Email found! Redirect to verification page", true)
                        }
                        _isLoading.value = false

                    } else {
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                        } else {
                            callback.getCallback("Email not found!", false)
                        }
                        _isLoading.value = false

                    }
                } else {
                    callback.getCallback(response.message(), false)
                    _isLoading.value = false

                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback.getCallback(t.message.toString(), false)
                _isLoading.value = false

            }
        })
    }

    fun getUser() : LiveData<UserLogin> {
        return user
    }
}