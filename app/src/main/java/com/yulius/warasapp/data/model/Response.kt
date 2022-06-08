package com.yulius.warasapp.data.model


data class Responses (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
)

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

data class ResponseQuotes (
    val results : ArrayList<Quotes>? = null,
    val page :Int? = null,
    val limit :Int? = null,
    val totalPages :Int? = null,
    val totalResult :Int? = null,
)

data class ResponseDailyReport (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : ArrayList<DailyReport>? = null
)

data class ResponseMaps(
    var status: String,
    var html_attributions: ArrayList<String>?= null,
    var results: ArrayList<ResultMaps>? = null,
)

data class ResponseFeedback (
    val status : String,
    val message : String? = null,
    val reason : String? = null,
    val data : Feedback? = null
)