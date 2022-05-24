package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Diagnose (
    var fever : Int,
    var cough : Int,
    var tired : Int,
    var soreThroat : Int,
    var cold : Int,
    var shortOfBreath: Int,
    var vomiting : Int,
) : Parcelable
