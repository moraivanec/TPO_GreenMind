package com.example.greenmind.data

import com.example.greenmind.data.local.IPlantDao
import com.example.greenmind.data.local.toLocal
import com.example.greenmind.data.local.toPlantDetailExternal
import com.example.greenmind.domain.IGardenRemoteRepository
import com.example.greenmind.domain.IPlantRepository
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val dataSource: IPlantDataSource,
    private val gardenRemoteRepository: IGardenRemoteRepository,
    private val plantDao: IPlantDao
) : IPlantRepository {

    override suspend fun fetchPlants(query: String): List<Plant> {
        return dataSource.getPlantList(query)
    }

    override suspend fun fetchPlantDetail(id: Int): PlantDetail {
        return dataSource.getPlantById(id)
    }

    override suspend fun savePlantInGarden(plantDetail: PlantDetail) {
        plantDao.insertSavedPlant(plantDetail.toLocal())

        try {
            gardenRemoteRepository.savePlant(plantDetail)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun removePlantFromGarden(id: Int) {
        plantDao.deleteSavedPlantById(id)

        try {
            gardenRemoteRepository.removePlant(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun isPlantSaved(id: Int): Boolean {
        return plantDao.getSavedPlantById(id) != null
    }

    override suspend fun getSavedPlants(): List<PlantDetail> {
        return plantDao.getSavedPlants().toPlantDetailExternal()
    }

    override suspend fun syncGardenFromRemote() {
        try {
            val remotePlants = gardenRemoteRepository.getSavedPlants()

            plantDao.insertSavedPlants(
                remotePlants.map { it.toLocal() }
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}