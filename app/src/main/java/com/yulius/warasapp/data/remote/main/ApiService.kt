package com.yulius.warasapp.data.remote.main

import com.yulius.warasapp.data.model.*
import retrofit2.Call
import retrofit2.http.*

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
        @Query("gender") gender: String,
        @Query("telephone") telephone: String,
        @Query("date_of_birth") date_of_birth: String,
    ): Call<ResponseData>

    @POST("users/changePassword")
    fun checkPassword(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @POST("email")
    fun checkEmail(
        @Query("email") email: String,
    ): Call<ResponseData>

    @PUT("users/changePassword")
    fun changePassword(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @PUT("users/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Query("username") username: String,
        @Query("full_name") full_name: String,
        @Query("email") email: String,
        @Query("gender") gender: String,
        @Query("telephone") telephone: String,
        @Query("date_of_birth") date_of_birth: String,
    ): Call<ResponseUpdate>

}