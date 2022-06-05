package com.yulius.warasapp.data.remote.maps

import com.yulius.warasapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfigMaps {
    companion object{
        fun getApiService(): ApiServiceMaps {
            val client = OkHttpClient.Builder()
                .addInterceptor{
                    val original = it.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("x-auth-token", BuildConfig.API_KEY)
                    val request = requestBuilder.build()
                    it.proceed(request)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiServiceMaps::class.java)
        }
    }
}