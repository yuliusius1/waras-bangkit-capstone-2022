package com.yulius.warasapp.data.remote.main

import com.yulius.warasapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
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
                .baseUrl("https://data-waras-api-service-hgz3km73yq-et.a.run.app/api/handler/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}