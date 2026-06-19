package com.example.greenmind.data

import android.util.Log
import com.example.greenmind.data.local.GreenMindDatabaseProvider
import com.example.greenmind.data.local.toExternal
import com.example.greenmind.data.local.toLocal

class PlantApiDataSource(
    private val plantAPI: IPlantAPI = RetrofitInstance.plantAPI
) : IPlantDataSource {

    private val TAG = "PLANT-API"

    companion object {
        private const val API_KEY = "sk-46Ft6a1614babec2f17640"
    }

    override suspend fun getPlantList(query: String): List<Plant> {
        val finalQuery = query.ifBlank { null }
        val dbLocal = GreenMindDatabaseProvider.dbLocal

        return try {
            Log.d(TAG, "PlantApiDataSource.getPlantList Search: $query")

            val plantResult = plantAPI.getPlantList(
                apiKey = API_KEY,
                query = finalQuery
            )

            Log.d(TAG, "PlantApiDataSource.getPlantList Result: ${plantResult.data.size}")

            val plants = plantResult.data
                .map { plantDto -> plantDto.toPlant() }
                .filter { plant -> plant.id != 0 }

            dbLocal.plantDao().insertPlants(plants.toLocal())

            Log.d(TAG, "API: plantas guardadas en Room: ${plants.size}")

            plants

        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener listado desde API. Buscando en Room...", e)

            val localPlants = dbLocal.plantDao().getPlants().toExternal()

            val filteredPlants = if (query.isBlank()) {
                localPlants
            } else {
                localPlants.filter { plant ->
                    plant.commonName.contains(query, ignoreCase = true) ||
                            plant.scientificName.contains(query, ignoreCase = true) ||
                            plant.careLevel.contains(query, ignoreCase = true)
                }
            }

            Log.d(TAG, "LOCAL: plantas recuperadas desde Room: ${filteredPlants.size}")

            if (filteredPlants.isNotEmpty()) {
                filteredPlants
            } else {
                throw e
            }
        }
    }

    override suspend fun getPlantById(id: Int): PlantDetail {
        val dbLocal = GreenMindDatabaseProvider.dbLocal

        return try {
            Log.d(TAG, "PlantApiDataSource.getPlantById ID: $id")

            val plantDetail = plantAPI.getPlantById(
                id = id,
                apiKey = API_KEY
            ).toPlantDetail()

            plantDetail

        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener detalle desde API. Buscando en Room...", e)

            val savedPlant = dbLocal
                .plantDao()
                .getSavedPlantById(id)

            if (savedPlant != null) {
                Log.d(TAG, "LOCAL: detalle recuperado desde Mi Jardín: ${savedPlant.commonName}")
                savedPlant.toExternal()
            } else {
                throw e
            }
        }
    }
}