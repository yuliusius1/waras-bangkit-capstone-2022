package com.yulius.warasapp.data.remote.main

import com.yulius.warasapp.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @POST("register")
    fun register(
        @Query("username") username: String,
        @Query("full_name") full_name: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("telephone") telephone: String,
        @Query("date_of_birth") date_of_birth: String,
    ): Call<ResponseData>

}