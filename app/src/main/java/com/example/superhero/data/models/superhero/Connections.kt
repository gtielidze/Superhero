package com.example.superhero.data.models.superhero


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Connections(
    @Json(name = "group-affiliation")
    val groupAffiliation: String?,
    @Embedded
    @Json(name = "relatives")
    val relatives: String?
) : Parcelable