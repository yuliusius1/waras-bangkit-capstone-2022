package com.yulius.warasapp.ui.profile.history.daily_report

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyReportViewModel(private val pref: UserPreference): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listReports = MutableLiveData<ArrayList<DailyReportList>>()
    val listReports : LiveData<ArrayList<DailyReportList>> = _listReports

    private var listReport = ArrayList<DailyReportList>()

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

                            for(i in 0..(history.day_to_heal?.toInt() ?: 1)){
                                var dailyReportList = DailyReportList(
                                    null,
                                    null,
                                    null,
                                    addTimes(i,history.created_at.substring(0,10)),
                                    null,
                                )
                                for (j in responseBody.data.indices){
                                    if (responseBody.data[j].id_user == history.id_user && responseBody.data[j].id_history == history.id_history &&
                                        responseBody.data[j].created_at.substring(0,10) == addTimes(i,history.created_at.substring(0,10))) {
                                        dailyReportList = DailyReportList(
                                            responseBody.data[j].daily_report,
                                            responseBody.data[j].id_user,
                                            responseBody.data[j].id_history,
                                            addTimes(i,history.created_at.substring(0,10)),
                                            responseBody.data[j].created_at.substring(0,10),
                                        )
                                    }
                                }
                                listReport.add(dailyReportList)
                            }
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

    fun sendDailyReport(report: String, data: History, callback: ResponseCallback){
        _isLoading.value = true
        ApiConfig.getApiService().sendDailyReport(
            report,
            data.id_user,
            data.id_history
        ).enqueue(object:
            Callback<ResponseDailyReport> {
            override fun onResponse(
                call: Call<ResponseDailyReport>,
                response: Response<ResponseDailyReport>
            ) {
                val responseBody= response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "Success"){
                        if(responseBody.message != null){
                            callback.getCallback(responseBody.message, true)
                            _isLoading.value = false
                        } else {
                            callback.getCallback("Add Daily Report Success", true)
                            _isLoading.value = false
                        }
                    } else {
                        if(responseBody.message != null){
                            callback.getCallback(responseBody.message, false)
                            _isLoading.value = false
                        } else {
                            callback.getCallback("Add Daily Report Failed", false)
                            _isLoading.value = false
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