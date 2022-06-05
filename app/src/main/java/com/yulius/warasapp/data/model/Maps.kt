package com.yulius.warasapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultMaps(
     var business_status : String? = null,
     var geometry : Geometry ,
     var icon : String? = null,
     var icon_background_color : String? = null,
     var icon_mask_base_uri : String? = null,
     var name : String? = null,
     var opening_hours : OpeningHours? = null,
     var photos : ArrayList<Photos>? = null,
     var place_id : String? = null,
     var plus_code : PlusCode? = null,
     var rating : Double? = null,
     var reference : String? = null,
     var scope : String? = null,
     var types : ArrayList<String>? = null,
     var user_rating_total : Int? = null,
     var vicinity : String? = null,
): Parcelable

@Parcelize
data class Geometry(
    var location: Location ,
    var viewport: Viewport ?= null,
): Parcelable

@Parcelize
data class Viewport(
    var northeast: Location ?= null,
    var southwest: Location ?= null,
): Parcelable

@Parcelize
data class OpeningHours(
    var open_now: String ?= null,
): Parcelable

@Parcelize
data class Photos(
    var height: Int?= null,
    var width: Int?= null,
    var html_attributions : ArrayList<String>?= null,
    var photo_reference : String?= null,
): Parcelable

@Parcelize
data class PlusCode(
    var compound_code : String?= null,
    var global_code : String?= null,
): Parcelable

@Parcelize
data class Location(
    var lat: Double,
    var lng: Double,
) : Parcelable