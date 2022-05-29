package com.yulius.warasapp.ui.profile.history.detail_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.main.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHistoryViewModel (private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var listSymptoms = ArrayList<String>()

    private val _listData = MutableLiveData<ArrayList<String>>()
    val listData : LiveData<ArrayList<String>> = _listData


    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun setSymptoms(idDiagnose: Int){
        _isLoading.value = true
        ApiConfig.getApiService().getDiagnoseByID(
            idDiagnose
        ).enqueue(object:
            Callback<ResponseDiagnoses> {
            override fun onResponse(
                call: Call<ResponseDiagnoses>,
                response: Response<ResponseDiagnoses>
            ) {
                val responseBody= response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success"){
                        if(responseBody.data != null) {
                            if(responseBody.data.fever == 1){
                                listSymptoms.add("Fever")
                            }

                            if(responseBody.data.cough == 1){
                                listSymptoms.add("Cough")
                            }

                            if(responseBody.data.tired == 1){
                                listSymptoms.add("Tired")
                            }

                            if(responseBody.data.sore_throat == 1){
                                listSymptoms.add("Sore Throat")
                            }

                            if(responseBody.data.runny_nose == 1){
                                listSymptoms.add("Runny Nose")
                            }

                            if(responseBody.data.short_breath == 1){
                                listSymptoms.add("Short of Breath")
                            }

                            if(responseBody.data.vomit == 1){
                                listSymptoms.add("Vomiting")
                            }

                            _listData.postValue(listSymptoms)
                        }
                    }
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseDiagnoses>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }
}