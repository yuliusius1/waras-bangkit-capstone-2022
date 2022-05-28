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

    private val _dataDiagnose = MutableLiveData<AddDiagnose?>()
    val dataDiagnose: LiveData<AddDiagnose?> = _dataDiagnose

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    val listData = ArrayList<AddDiagnose>()

    fun saveData(dataDiagnose: Diagnose, dayToHeal: Int, userId : Int, callback: ResponseCallback) {
        _isLoading.value = true
        ApiConfig.getApiService().addDiagnoses(
            dataDiagnose.age,
            dataDiagnose.gender,
            dataDiagnose.fever,
            dataDiagnose.cough,
            dataDiagnose.tired,
            dataDiagnose.sore_throat,
            dataDiagnose.runny_nose,
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
                            getLastDataUser(userId)
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

    private fun getLastDataUser(userId: Int) {
        _isLoading.value = true
        ApiConfig.getApiService().getAllDiagnose(
        ).enqueue(object:
            Callback<ResponseAllDiagnoses> {
            override fun onResponse(
                call: Call<ResponseAllDiagnoses>,
                response: Response<ResponseAllDiagnoses>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success") {
                        if(responseBody.message != null){
                            if(responseBody.data != null){
                                for (i in responseBody.data.indices){
                                    if (responseBody.data[i].id_user == userId) {
                                        listData.add(responseBody.data[i])
                                    }
                                }
                                _dataDiagnose.postValue(listData.last())
                            }
                            _isLoading.value = false
                        } else {
                            _isLoading.value = false
                        }
                    } else {
                        if(responseBody.message != null){
                            _isLoading.value = false
                        } else {
                            _isLoading.value = false
                        }
                    }
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseAllDiagnoses>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun saveHistory( dataDiagnose: AddDiagnose, date_to_heal: String, userId : Int, callback: ResponseCallback) {
        _isLoading.value = true
        ApiConfig.getApiService().sendHistory(
            dataDiagnose.day_to_heal,
            date_to_heal,
            "Proses",
            "Hal yang baik adalah tidak buruk",
            userId,
            dataDiagnose.id_diagnose,
        ).enqueue(object:
            Callback<ResponseHistory> {
            override fun onResponse(
                call: Call<ResponseHistory>,
                response: Response<ResponseHistory>
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

            override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {
                callback.getCallback(t.message.toString(),false)
                _isLoading.value = false
            }
        })
    }
}