package com.yulius.warasapp.ui.hospital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.yulius.warasapp.data.model.*
import com.yulius.warasapp.data.remote.maps.ApiConfigMaps
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel (private val pref: UserPreference) : ViewModel()  {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listMaps = MutableLiveData<ArrayList<ResultMaps>>()
    private val listData = ArrayList<ResultMaps>()

    fun setMaps(location: LatLng){
        _isLoading.value = true
        ApiConfigMaps.getApiService().getNearbyHospitalLocation("hospital","${location.latitude},${location.longitude}",5000,"AIzaSyDQshNAOJs1KO57gdY87fVCX9gcSwqfHjk"
        ).enqueue(object:
            Callback<ResponseMaps> {
            override fun onResponse(
                call: Call<ResponseMaps>,
                response: Response<ResponseMaps>
            ) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    if(responseBody.status == "OK"){
                        if(responseBody.results != null){
                            for (i in responseBody.results!!.indices){
                                listData.add(responseBody.results!![i])
                            }
                            _listMaps.postValue(listData)
                        }
                    }
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseMaps>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun getMaps() : LiveData<ArrayList<ResultMaps>> {
        return _listMaps
    }
}