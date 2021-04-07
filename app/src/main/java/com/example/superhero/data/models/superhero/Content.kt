package com.example.superhero.data.models.superhero

import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class Content <T>(
    @Json(name = "content")
    val content: T
    )
