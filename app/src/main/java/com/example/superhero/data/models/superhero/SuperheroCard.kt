package com.example.superhero.data.models.superhero


import android.os.Parcelable
import com.squareup.moshi.Json
import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
@Entity
data class SuperheroCard(
    @Json(name = "id")
    @PrimaryKey
    val id: Int,
    @Json(name = "appearance")
    @Embedded
    val appearance: Appearance?,
    @Json(name = "biography")
    @Embedded
    val biography: Biography?,
    @Json(name = "connections")
    @Embedded
    val connections: Connections?,
    @Json(name = "image")
    @Embedded
    val image: Image?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "powerstats")
    @Embedded
    val powerstats: Powerstats?,
    @Json(name = "work")
    @Embedded
    var work: Work?
) : Parcelable

