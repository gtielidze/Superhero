package com.example.superhero.data.models.superhero


import com.squareup.moshi.Json
import androidx.annotation.Keep


@Keep
data class PaginatedData <T>(
    @Json(name = "content")
    val content: T,
    @Json(name = "totalCount")
    val totalCount: Int,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "isLast")
    val isLast: Boolean
)