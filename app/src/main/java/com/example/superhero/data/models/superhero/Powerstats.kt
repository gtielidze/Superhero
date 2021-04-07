package com.example.superhero.data.models.superhero


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
data class Powerstats(
    @Json(name = "combat")
    val combat: String?,
    @Json(name = "durability")
    @Embedded
    val durability: String?,
    @Json(name = "intelligence")
    val intelligence: String?,
    @Json(name = "power")
    val power: String?,
    @Json(name = "speed")
    val speed: String?,
    @Json(name = "strength")
    val strength: String?
) : Parcelable