package com.yulius.warasapp.data.remote.main

import com.yulius.warasapp.data.model.Article
import com.yulius.warasapp.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun getUser(
        @Body searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String,
    ): Call<User>
}