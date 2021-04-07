package com.example.superhero.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.superhero.data.models.superhero.SuperheroCard



@Dao
interface CardDao {

    @Query("select * from superheroCard" )
    suspend fun getAll(): List<SuperheroCard>

    @Query("select * from superheroCard where id=:id")
    suspend fun getById(id: Int): SuperheroCard?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(superheroCard: SuperheroCard)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(superheroCard: List<SuperheroCard>)

}