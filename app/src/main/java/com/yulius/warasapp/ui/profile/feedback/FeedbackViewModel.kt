package com.yulius.warasapp.ui.profile.feedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.ResponseFeedback
import com.yulius.warasapp.data.model.User
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.util.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedbackViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun sendFeedback(feedback:String, userId: Int, rating: Double, callback: ResponseCallback){
        _isLoading.value = true
        ApiConfig.getApiService().sendFeedback(
            feedback,
            rating,
            userId
        ).enqueue(object:
            Callback<ResponseFeedback> {
            override fun onResponse(
                call: Call<ResponseFeedback>,
                response: Response<ResponseFeedback>
            ) {
                val responseBody= response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success"){
                        if(responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                            _isLoading.value = false
                        } else {
                            callback.getCallback("Add Feedback Success", true)
                            _isLoading.value = false
                        }
                    } else {
                        if(responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                            _isLoading.value = false
                        } else {
                            callback.getCallback("Add Feedback Failed", false)
                            _isLoading.value = false
                        }
                    }
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }
            override fun onFailure(call: Call<ResponseFeedback>, t: Throwable) {
                _isLoading.value = false
            }
        })

    }
}