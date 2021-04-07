package com.example.superhero.data.models.superhero


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Work(
    @Json(name = "base")
    val base: String?,
    @Embedded
    @Json(name = "occupation")
    val occupation: String?
) : Parcelable