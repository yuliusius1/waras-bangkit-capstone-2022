package com.yulius.warasapp.data.remote.maps

import com.yulius.warasapp.data.model.ResponseMaps
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceMaps {
    @GET("json")
    fun getNearbyHospitalLocation(
        @Query("types") types: String,
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("key") key: String,
    ): Call<ResponseMaps>
}