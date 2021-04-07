package com.example.superhero.data.models.user


import com.squareup.moshi.Json
import androidx.annotation.Keep

/*ჯეისონის დატა კლასი, აპიდან წამოღებული იუზერკონტროლერი*/

@Keep
data class UserSession(
    @Json(name = "accessToken")
    val accessToken: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "userName")
    val userName: String
)