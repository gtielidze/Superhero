package com.example.superhero.data.models.user


import com.squareup.moshi.Json
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey


/**აპიდან წამოღებული იუზერპროფილის კლასი */
@Keep
@Entity
data class UserProfile(
    @Json(name = "imageUrl")
    val imageUrl: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "userName")
    val userName: String,
    @PrimaryKey
    val id: Int = 1
)