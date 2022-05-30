package com.yulius.warasapp.data.model

data class ResponseData (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : UserLogin? = null
)

data class ResponseUpdate (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : UserUpdate? = null
)

data class ResponseDiagnoses (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : AddDiagnose? = null
)

data class ResponseAllDiagnoses (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : ArrayList<AddDiagnose>? = null
)

data class ResponseRecommendation (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : Recommendation? = null
)

data class ResponseAllHistory (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : ArrayList<History>? = null
)

data class ResponseHistory (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : History? = null
)

data class ResponseReport (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : Reports? = null
)