package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val full_name: String,
    val username: String,
    val email: String,
    val password: String,
    val gender: String,
    val telephone: String,
    val date_of_birth: String,
    val isLogin: Boolean,
    val created_at: String,
    val updated_at: String,
    val id: Int,
) : Parcelable

@Parcelize
data class UserRegister(
    var full_name: String,
    var username: String,
    var email: String,
    var password: String,
    var gender: String,
    var telephone: String,
    var date_of_birth: String,
) : Parcelable

@Parcelize
data class UserLogin(
    var full_name: String,
    var username: String,
    var email: String,
    var password: String,
    var gender: String,
    var telephone: String,
    var date_of_birth: String,
    val created_at: String,
    val updated_at: String,
    val id: Int,
) : Parcelable

@Parcelize
data class UserUpdate(
    var full_name: String,
    var username: String,
    var email: String,
    var gender: String,
    var telephone: String,
    var date_of_birth: String,
) : Parcelable
