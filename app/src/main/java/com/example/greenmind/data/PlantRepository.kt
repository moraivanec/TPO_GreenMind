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

    // Obtiene el listado de plantas desde el DataSource
    override suspend fun fetchPlants(query: String): List<Plant> {
        return dataSource.getPlantList(query)
    }

    // Obtiene el detalle de una planta específica desde el DataSource
    override suspend fun fetchPlantDetail(id: Int): PlantDetail {
        return dataSource.getPlantById(id)
    }

    // Guarda una planta en Mi Jardín.
    // Primero la guarda en Room y luego intenta sincronizarla con Firestore
    override suspend fun savePlantInGarden(plantDetail: PlantDetail) {
        plantDao.insertSavedPlant(plantDetail.toLocal())

        try {
            gardenRemoteRepository.savePlant(plantDetail)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Elimina una planta de Mi Jardín.
    // Primero la elimina de Room y luego intenta eliminarla de Firestore
    override suspend fun removePlantFromGarden(id: Int) {
        plantDao.deleteSavedPlantById(id)

        try {
            gardenRemoteRepository.removePlant(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Verifica si una planta ya está guardada en Mi Jardín
    override suspend fun isPlantSaved(id: Int): Boolean {
        return plantDao.getSavedPlantById(id) != null
    }

    // Obtiene las plantas guardadas en Mi Jardín desde Room
    override suspend fun getSavedPlants(): List<PlantDetail> {
        return plantDao.getSavedPlants().toPlantDetailExternal()
    }

    // Sincroniza las plantas guardadas en Firestore con la base local Room
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