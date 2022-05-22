package com.yulius.warasapp.ui.profile.change_password

import androidx.lifecycle.*
import com.yulius.warasapp.data.model.ResponseData
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.util.ResponseCallback
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel(private val pref: UserPreference) : ViewModel()  {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveUser(user)
        }
    }

    fun changePassword(user: User, password: String, callback: ResponseCallback){
        ApiConfig.getApiService().changePassword(user.username, password).enqueue(object:
            Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Succes" && responseBody.data != null){
                        saveUser(
                            User(
                                user.full_name,
                                user.username,
                                user.email,
                                responseBody.data.password,
                                user.telephone,
                                user.date_of_birth,
                                user.isLogin,
                                user.created_at,
                                responseBody.data.updated_at,
                                user.id
                            ))

                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                        } else {
                            callback.getCallback("Success Update Password", true)
                        }
                    } else {
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                        } else {
                            callback.getCallback("Failed Update Password", false)
                        }
                    }
                } else {
                    callback.getCallback(response.message(), false)
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback.getCallback(t.message.toString(), false)
            }

        })

    }

    fun checkPassword(username: String, password: String, callback: ResponseCallback){
        ApiConfig.getApiService().checkPassword(username, password).enqueue(object:
            Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success"){
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                        } else {
                            callback.getCallback("Success", true)
                        }
                    } else {
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                        } else {
                            callback.getCallback("Error Update Password", false)
                        }
                    }
                } else {
                    callback.getCallback(response.message(), false)
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                callback.getCallback(t.message.toString(), false)
            }

        })
    }
}