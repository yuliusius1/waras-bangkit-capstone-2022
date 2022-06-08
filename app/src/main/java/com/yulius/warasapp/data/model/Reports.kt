package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reports (
    var report: String,
    var status: String,
    var id_user: String,
    var id_history: String,
    var created_at: String,
) : Parcelable