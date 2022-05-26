package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Diagnose (
    var age : Int,
    var gender: Int,
    var fever : Int,
    var cough : Int,
    var tired : Int,
    var sore_throat : Int,
    var cold : Int,
    var short_breath: Int,
    var vomit : Int,
) : Parcelable

@Parcelize
data class AddDiagnose (
    var id_diagnose: Int,
    var age : Int,
    var gender: Int,
    var fever : Int,
    var cough : Int,
    var tired : Int,
    var sore_throat : Int,
    var cold : Int,
    var short_breath: Int,
    var vomit : Int,
    var day_to_heal : Int,
    var created_at : String,
    var id_user : Int,
) : Parcelable
