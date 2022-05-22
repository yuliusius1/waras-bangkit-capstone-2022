package com.yulius.warasapp.ui.auth.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yulius.warasapp.data.model.ResponseData
import com.yulius.warasapp.data.model.UserLogin
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.util.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordViewModel (private val pref: UserPreference): ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun resetPassword(username: String, password: String, callback: ResponseCallback){
        _isLoading.value = true
        ApiConfig.getApiService().changePassword(username,password).enqueue(object:
            Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success" ){

                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                        } else {
                            callback.getCallback("Success Reset Password", true)
                        }
                        _isLoading.value = false

                    } else {
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                        } else {
                            callback.getCallback("Failed Reset Password", false)
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
}