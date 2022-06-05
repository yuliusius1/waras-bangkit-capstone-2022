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

@Parcelize
data class DailyReportList(
    var daily_report: String? = null,
    var id_user: Int? = null,
    var id_history: Int? = null,
    var created_date: String? = null,
    var created_at: String? = null
) : Parcelable