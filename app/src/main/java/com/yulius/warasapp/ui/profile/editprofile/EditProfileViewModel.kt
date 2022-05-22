package com.yulius.warasapp.ui.profile.editprofile

import android.util.Log
import androidx.lifecycle.*
import com.yulius.warasapp.data.model.ResponseUpdate
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.util.ResponseCallback
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel(private val pref: UserPreference) : ViewModel() {
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

    fun updateUser(user: User, fullName: String, email: String, telephone: String, date:String , callback: ResponseCallback){
        _isLoading.value = true
        ApiConfig.getApiService().updateUser(user.id,user.username,fullName,email,telephone,date).enqueue(object:
            Callback<ResponseUpdate> {
            override fun onResponse(
                call: Call<ResponseUpdate>,
                response: Response<ResponseUpdate>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Succes" && responseBody.data != null){
                        Log.d("TAG", "onResponse: ${responseBody.data}")
                        saveUser(
                            User(
                               responseBody.data.full_name,
                               responseBody.data.username,
                               responseBody.data.email,
                                user.password,
                               responseBody.data.telephone,
                               responseBody.data.date_of_birth,
                                user.isLogin,
                                user.created_at,
                                user.updated_at,
                                user.id
                            ))
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                        } else {
                            callback.getCallback("Success Update Data", true)
                        }
                        _isLoading.value = false

                    } else {
                        if (responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                        } else {
                            callback.getCallback("Failed Update Data", false)
                        }
                        _isLoading.value = false

                    }
                } else {
                    callback.getCallback(response.message(), false)
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseUpdate>, t: Throwable) {
                callback.getCallback(t.message.toString(), false)
                _isLoading.value = false
            }
        })
    }

}