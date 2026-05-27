package com.example.greenmind.data

import com.example.greenmind.domain.IPlantRepository

class PlantRepository(
    private val plantDataSource: IPlantDataSource = PlantApiDataSource()
) : IPlantRepository {

    override suspend fun fetchPlants(query: String): List<Plant> {
        return plantDataSource.getPlantList(query)
    }

    override suspend fun fetchPlantDetail(id: Int): PlantDetail {
        return plantDataSource.getPlantById(id)
    }
}