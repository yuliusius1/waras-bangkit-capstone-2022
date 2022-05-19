package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val full_name: String,
    val username: String,
    val email: String,
    val password: String,
    val telephone: String,
    val date_of_birth: String,
    val isLogin: Boolean,
    val created_at: String,
    val updated_at: String,
    val id: Int,
) : Parcelable