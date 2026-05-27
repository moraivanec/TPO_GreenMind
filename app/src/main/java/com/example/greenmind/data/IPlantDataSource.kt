package com.example.greenmind.data

interface IPlantDataSource {

    suspend fun getPlantList(query: String = ""): List<Plant>

    suspend fun getPlantById(id: Int): PlantDetail
}