package com.example.superhero.data.network

import com.example.superhero.data.models.superhero.Content
import com.example.superhero.data.models.superhero.PaginatedData
import com.example.superhero.data.models.superhero.SuperheroCard
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface SuperheroService {
    @GET("/comics/list-characters")
    suspend fun getCards(
        @Query("query",  encoded = false) query: String? = null,
        @Query("offset") offset: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): PaginatedData<List<SuperheroCard>>

    @GET("/comics/search")
    suspend fun searchCards(
        @Query("query") query: String? = null,
        @Query("offset") offset: Int? = 1,
        @Query("pageSize") pageSize: Int? = 500
    ): PaginatedData<List<SuperheroCard>>


    @GET("/comics/get{id}")
    suspend fun getCardBydId(
        @Path("id") id: Int,
    ): Content<SuperheroCard>
}