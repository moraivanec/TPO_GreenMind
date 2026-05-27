package com.example.greenmind.domain

import com.example.greenmind.data.Plant
import com.example.greenmind.data.PlantDetail

interface IPlantRepository {

    suspend fun fetchPlants(query: String = ""): List<Plant>

    suspend fun fetchPlantDetail(id: Int): PlantDetail
}