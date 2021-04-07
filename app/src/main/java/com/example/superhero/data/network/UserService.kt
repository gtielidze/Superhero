package com.example.superhero.data.network

import com.example.superhero.data.models.user.UserProfile
import com.example.superhero.data.models.user.UserRegistrationRequest
import com.example.superhero.data.models.user.UserSession
import retrofit2.http.*


interface UserService {
    //გადაეცემა 2 პარამეტრი (Query და ცვლადი)
    //და აბრუნებს რეტროფიტის სერვისის Call ტიპის ობიექტს ჯეისონის კლასთან ერთად(Call<UserSession>)
    //ანოტაცია/მისამართი
    @POST("/auth/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): UserSession

    @POST("/auth/register")
    suspend fun register(@Body request: UserRegistrationRequest)

    @GET("/auth/user")
    suspend fun getUser(): UserProfile

    @GET("/user/comics/get-my-characters")
    suspend fun getUserCards(): List<Int>

    @POST("/user/comics/save-character")
    suspend fun saveUserCards(@Query("characterId") characterId: Int)

    @DELETE("/user/comics/delete-my-character")
    suspend fun deleteUserCard(@Query("characterId") characterId: Int)


}