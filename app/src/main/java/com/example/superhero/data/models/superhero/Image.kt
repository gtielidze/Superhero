package com.example.superhero.data.models.superhero


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Image(
    @Json(name = "url")
    val url: String?
) : Parcelable