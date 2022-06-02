package com.yulius.warasapp.data.remote.quotes

import com.yulius.warasapp.BuildConfig
import com.yulius.warasapp.data.remote.main.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfigQuotes {
    companion object{
        fun getApiService(): ApiServiceQuotes {
            val client = OkHttpClient.Builder()
                .addInterceptor{
                    val original = it.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("X-RapidAPI-Host", "world-of-quotes.p.rapidapi.com")
                        .addHeader("X-RapidAPI-Key", "16841ac6b5msh721cc5fa7cc9021p147444jsn7d45a1721e39")
                    val request = requestBuilder.build()
                    it.proceed(request)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://world-of-quotes.p.rapidapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiServiceQuotes::class.java)
        }
    }
}