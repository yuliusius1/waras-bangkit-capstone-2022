package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val isLogin: Boolean,
) : Parcelable