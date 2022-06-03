package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyReport(
    var id_dailyreport: Int,
    var daily_report: String? = null,
    var created_at: String,
    var id_user: Int,
    var id_history: Int
) : Parcelable