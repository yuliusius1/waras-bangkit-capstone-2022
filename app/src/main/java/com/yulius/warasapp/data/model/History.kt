package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History (
    var id_history : Int,
    var day_to_heal: String? = null,
    var date_to_heal: String? = null,
    var status: String? = null,
    var recommendations : String? = null,
    var created_at : String,
    var id_user : Int,
    var id_diagnose : Int,
): Parcelable