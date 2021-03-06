package com.example.superhero.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.superhero.data.models.user.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Query("select * from userprofile where id = 1 ")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert
    fun insert(userProfile: UserProfile)

    @Query("delete from userprofile")
    fun delete()
}