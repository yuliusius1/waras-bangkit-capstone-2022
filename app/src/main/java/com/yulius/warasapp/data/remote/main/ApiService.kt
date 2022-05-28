package com.yulius.warasapp.data.remote.main

import com.yulius.warasapp.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @POST("login")
    fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
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

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @POST("users/changePassword")
    fun checkPassword(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @POST("email")
    fun checkEmail(
        @Query("email") email: String,
    ): Call<ResponseData>

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @PUT("users/changePassword")
    fun changePassword(
        @Query("username") username: String,
        @Query("password") password: String,
    ): Call<ResponseData>

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
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

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @POST("diagnoses")
    fun addDiagnoses(
        @Query("age") age: Int,
        @Query("gender") gender: Int,
        @Query("fever") fever: Int,
        @Query("cough") cough: Int,
        @Query("tired") tired: Int,
        @Query("sore_throat") sore_throat: Int,
        @Query("runny_nose") runny_nose: Int,
        @Query("short_breath") short_breath: Int,
        @Query("vomit") vomit: Int,
        @Query("day_to_heal") day_to_heal: Int,
        @Query("id_user") id_user: Int,
    ): Call<ResponseDiagnoses>

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @GET("diagnoses")
    fun getAllDiagnose(
    ): Call<ResponseAllDiagnoses>

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @GET("diagnoses")
    fun getDiagnoseByID(
        @Path("id") id: String,
    ): Call<ResponseDiagnoses>

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @GET("history")
    fun getAllHistory(
    ): Call<ResponseHistory>

    @Headers("x-auth-token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6ImFkbWluIiwiaWF0IjoxNjUzNTQ4MjMzfQ.MlpzIBB5Ko30qPtxxl422bUVHL1BtcA2Z3SJuFdULEw")
    @GET("history")
    fun sendHistory(
        @Query("day_to_heal") day_to_heal: Int,
        @Query("date_to_heal") date_to_heal: String,
        @Query("status") status: String,
        @Query("recommendation") recommendation: String,
        @Query("id_user") id_user: Int,
        @Query("id_diagnose") id_diagnose: Int,
    ): Call<ResponseHistory>
}