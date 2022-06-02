package com.yulius.warasapp.data.remote.quotes

import com.yulius.warasapp.data.model.ResponseQuotes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceQuotes {
    @GET("quotes")
    fun getQuotes(
        @Query("category") category: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): Call<ResponseQuotes>
}