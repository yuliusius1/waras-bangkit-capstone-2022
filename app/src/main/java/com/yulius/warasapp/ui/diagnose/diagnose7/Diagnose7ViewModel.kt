package com.yulius.warasapp.ui.diagnose.diagnose7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.util.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Diagnose7ViewModel(private val pref: UserPreference) : ViewModel()  {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun saveData(dataDiagnose: Diagnose, dayToHeal: Int, userId : Int, callback: ResponseCallback) {
        _isLoading.value = true
        ApiConfig.getApiService().addDiagnoses(
            dataDiagnose.age,
            dataDiagnose.gender,
            dataDiagnose.fever,
            dataDiagnose.cough,
            dataDiagnose.tired,
            dataDiagnose.sore_throat,
            dataDiagnose.cold,
            dataDiagnose.short_breath,
            dataDiagnose.vomit,
            dayToHeal,
            userId
        ).enqueue(object:
            Callback<ResponseDiagnoses> {
            override fun onResponse(
                call: Call<ResponseDiagnoses>,
                response: Response<ResponseDiagnoses>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success") {
                        if(responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                            _isLoading.value = false
                        } else {
                            callback.getCallback("Add Diagnose Success", true)
                            _isLoading.value = false
                        }
                    } else {
                        if(responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                            _isLoading.value = false
                        } else {
                            callback.getCallback("Add Diagnose Failed", false)
                            _isLoading.value = false
                        }
                    }
                } else {
                    callback.getCallback(response.message(),false)
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseDiagnoses>, t: Throwable) {
                callback.getCallback(t.message.toString(),false)
                _isLoading.value = false
            }
        })
    }
}