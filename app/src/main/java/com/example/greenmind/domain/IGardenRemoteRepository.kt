package com.example.greenmind.domain

import com.example.greenmind.data.PlantDetail

interface IGardenRemoteRepository {

    suspend fun savePlant(plantDetail: PlantDetail)

    suspend fun removePlant(id: Int)

    suspend fun getSavedPlants(): List<PlantDetail>
}