package com.yulius.warasapp.data.model

data class Recommendation (
    var id_recommendations : Int,
    var recommendation: String,
    var created_at: String,
    var id_user: Int? = null
)