package com.yulius.warasapp.ui.home

import androidx.lifecycle.*
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.main.ApiConfig
import com.yulius.warasapp.data.remote.quotes.ApiConfigQuotes
import com.yulius.warasapp.util.ResponseCallback
import com.yulius.warasapp.util.addTimes
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val pref: UserPreference) : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val listQuotes = MutableLiveData<ResponseQuotes>()
    val lastHistory = MutableLiveData<ResponseHistory>()
    private var listReport = ArrayList<DailyReportList>()
    private val _listReports = MutableLiveData<ArrayList<DailyReportList>>()
    val listReports : LiveData<ArrayList<DailyReportList>> = _listReports

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun setQuote(randomInt: Int){
        _isLoading.value = true
        ApiConfigQuotes.getApiService().getQuotes("health",1,randomInt
        ).enqueue(object:
            Callback<ResponseQuotes> {
            override fun onResponse(
                call: Call<ResponseQuotes>,
                response: Response<ResponseQuotes>
            ) {
                if(response.isSuccessful){
                    listQuotes.postValue(response.body())
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseQuotes>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun getQuotes() : LiveData<ResponseQuotes> {
        return listQuotes
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
                            listReport.clear()
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
                                        responseBody.data[j].created_at.substring(0,10) == addTimes(i,history.created_at.substring(0,10))
                                    ) {
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

    fun setLastHistory(userId: Int){
        _isLoading.value = true
        ApiConfig.getApiService().getLastHistory(userId
        ).enqueue(object:
            Callback<ResponseHistory> {
            override fun onResponse(
                call: Call<ResponseHistory>,
                response: Response<ResponseHistory>
            ) {
                if(response.isSuccessful){
                    lastHistory.postValue(response.body())
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun getLastHistory() : LiveData<ResponseHistory> {
        return lastHistory
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