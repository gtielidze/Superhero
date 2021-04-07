package com.example.superhero.data.models.user


import com.squareup.moshi.Json
import androidx.annotation.Keep

@Keep
data class UserRegistrationRequest(
    @Json(name = "name")
    val name: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "userName")
    val userName: String
)