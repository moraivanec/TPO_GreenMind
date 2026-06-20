package com.example.greenmind.domain

import com.example.greenmind.data.Plant
import com.example.greenmind.data.PlantDetail

interface IPlantRepository {

    suspend fun fetchPlants(query: String = ""): List<Plant>

    suspend fun fetchPlantDetail(id: Int): PlantDetail

    suspend fun savePlantInGarden(plantDetail: PlantDetail)

    suspend fun removePlantFromGarden(id: Int)

    suspend fun isPlantSaved(id: Int): Boolean

    suspend fun getSavedPlants(): List<PlantDetail>
}