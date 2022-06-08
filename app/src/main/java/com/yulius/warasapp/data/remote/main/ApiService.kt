package com.yulius.warasapp.data.remote.main

import com.yulius.warasapp.BuildConfig
import com.yulius.warasapp.data.model.*
import org.checkerframework.common.reflection.qual.GetClass
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

    @GET("users/username")
    fun checkUser(
        @Query("username") username: String,
    ): Call<ResponseData>

    @GET("diagnoses")
    fun getAllDiagnose(
    ): Call<ResponseAllDiagnoses>

    @GET("diagnoses/{id}")
    fun getDiagnoseByID(
        @Path("id") id: Int,
    ): Call<ResponseDiagnoses>

    @GET("history")
    fun getAllHistory(
    ): Call<ResponseAllHistory>

    @POST("history")
    fun sendHistory(
        @Query("day_to_heal") day_to_heal: Int,
        @Query("date_to_heal") date_to_heal: String,
        @Query("status") status: String,
        @Query("recommendations") recommendations: String,
        @Query("id_user") id_user: Int,
        @Query("id_diagnose") id_diagnose: Int,
    ): Call<ResponseHistory>

    @PUT("users/changeHistory/{id}")
    fun changeHistory(
        @Path("id") id: Int,
        @Query("status") status: String,
    ): Call<Responses>

    @POST("reports")
    fun sendReport(
        @Query("report") report: String,
        @Query("status") status: String,
        @Query("id_user") id_user: Int,
        @Query("id_history") id_history: Int,
    ): Call<ResponseReport>

    @POST("dailyreports")
    fun sendDailyReport(
        @Query("daily_report") daily_report: String,
        @Query("id_user") id_user: Int,
        @Query("id_history") id_history: Int,
    ): Call<ResponseDailyReport>

    @POST("feedback")
    fun sendFeedback(
        @Query("feedback") feedback: String,
        @Query("rating") rating: Double,
        @Query("id_user") id_user: Int,
    ): Call<ResponseFeedback>

    @GET("dailyreports")
    fun getAllDailyReport(
    ): Call<ResponseDailyReport>

    @GET("lastHistory/{id}")
    fun getLastHistory(
        @Path("id") id: Int,
    ): Call<ResponseHistory>
}