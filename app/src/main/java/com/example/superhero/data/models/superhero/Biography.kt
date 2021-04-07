package com.example.superhero.data.models.superhero


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Biography(
    @Json(name = "aliases")
    val aliases: List<String>? = emptyList(),
    @Json(name = "alignment")
    val alignment: String?,
    @Embedded
    @Json(name = "alter-egos")
    val alterEgos: String?,
    @Json(name = "first-appearance")
    val firstAppearance: String?,
    @Json(name = "full-name")
    val fullName: String?,
    @Json(name = "place-of-birth")
    val placeOfBirth: String?,
    @Json(name = "publisher")
    val publisher: String?
) : Parcelable