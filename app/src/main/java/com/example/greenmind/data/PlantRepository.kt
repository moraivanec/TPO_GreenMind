package com.example.greenmind.data

import com.example.greenmind.data.local.GreenMindDatabaseProvider
import com.example.greenmind.data.local.toLocal
import com.example.greenmind.data.local.toPlantDetailExternal
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

    override suspend fun savePlantInGarden(plantDetail: PlantDetail) {
        val dbLocal = GreenMindDatabaseProvider.dbLocal

        dbLocal
            .plantDao()
            .insertSavedPlant(plantDetail.toLocal())
    }

    override suspend fun removePlantFromGarden(id: Int) {
        val dbLocal = GreenMindDatabaseProvider.dbLocal

        dbLocal
            .plantDao()
            .deleteSavedPlantById(id)
    }

    override suspend fun isPlantSaved(id: Int): Boolean {
        val dbLocal = GreenMindDatabaseProvider.dbLocal

        return dbLocal
            .plantDao()
            .getSavedPlantById(id) != null
    }

    override suspend fun getSavedPlants(): List<PlantDetail> {
        val dbLocal = GreenMindDatabaseProvider.dbLocal

        return dbLocal
            .plantDao()
            .getSavedPlants()
            .toPlantDetailExternal()
    }
}