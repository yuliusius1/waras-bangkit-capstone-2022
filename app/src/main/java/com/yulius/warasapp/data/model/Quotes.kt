package com.yulius.warasapp.data.model

data class Quotes(
    var isActive : Boolean?= false,
    var quote: String?= null,
    var author: String?= null,
    var category: String?= null,
    var id: String?= null,
)