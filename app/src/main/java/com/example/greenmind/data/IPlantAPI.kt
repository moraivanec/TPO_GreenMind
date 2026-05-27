package com.example.greenmind.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IPlantAPI {

    @GET("species-list")
    suspend fun getPlantList(
        @Query("key") apiKey: String,
        @Query("q") query: String? = null
    ): PlantListResponse

    @GET("species/details/{id}")
    suspend fun getPlantById(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): PlantDetailDto
}