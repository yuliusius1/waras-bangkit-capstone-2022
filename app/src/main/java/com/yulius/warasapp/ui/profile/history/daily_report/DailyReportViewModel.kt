package com.yulius.warasapp.ui.profile.history.daily_report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.main.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyReportViewModel(private val pref: UserPreference): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listReports = MutableLiveData<ArrayList<String>>()
    val listReports : LiveData<ArrayList<String>> = _listReports

    private var listReport = ArrayList<String>()
    private var listReportUser = ArrayList<String?>()


    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun setData(history: History){
        _isLoading.value = true
        ApiConfig.getApiService().getAllDailyReport(
        ).enqueue(object:
            Callback<ResponseDailyReport> {
            override fun onResponse(
                call: Call<ResponseDailyReport>,
                response: Response<ResponseDailyReport>
            ) {
                val responseBody= response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success"){
                        if(responseBody.data != null) {
                            Log.d("TAG", "onResponseBody: ${responseBody.data}")
                            for (i in responseBody.data.indices){
                                if (responseBody.data[i].id_user == history.id_user && responseBody.data[i].id_history == history.id_history) {
                                    listReportUser.add(responseBody.data[i].daily_report.toString())
                                    listReport.add(responseBody.data[i].daily_report.toString())
                                }
                            }
                            Log.d("TAG", "onResponseBody: $listReportUser and size ${listReportUser.size}")

                            for(i in listReportUser.size..(history.day_to_heal?.toInt() ?: 1)){
                                listReport.add("")
                            }
                            Log.d("TAG", "onResponseBody: $listReport and size ${listReport.size}")
                            _listReports.postValue(listReport)
                        }
                    }
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseDailyReport>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }


}