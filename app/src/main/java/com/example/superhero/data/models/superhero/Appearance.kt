package com.example.superhero.data.models.superhero


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class Appearance(
    @Json(name = "eye-color")
    val eyeColor: String?,
    @Json(name = "gender")
    val gender: String?,
    @Json(name = "hair-color")
    val hairColor: String?,
    @Json(name = "height")
    val height: List<String>? = null,
    @Json(name = "race")
    val race: String?,
    @Json(name = "weight")
    val weight: List<String>? = null
) : Parcelable