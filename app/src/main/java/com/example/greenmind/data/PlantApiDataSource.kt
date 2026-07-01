package com.example.greenmind.data

import android.util.Log
import com.example.greenmind.data.local.IPlantDao
import com.example.greenmind.data.local.toExternal
import com.example.greenmind.data.local.toLocal
import javax.inject.Inject
import com.example.greenmind.BuildConfig

class PlantApiDataSource @Inject constructor(
    private val plantAPI: IPlantAPI,
    private val plantDao: IPlantDao
) : IPlantDataSource {

    companion object {
        private const val TAG = "PLANT-API"
        private val API_KEY = BuildConfig.PERENUAL_API_KEY
    }

    override suspend fun getPlantList(query: String): List<Plant> {
        val finalQuery = query.ifBlank { null }

        return try {
            Log.d(TAG, "PlantApiDataSource.getPlantList Search: $query")

            val plantResult = plantAPI.getPlantList(
                apiKey = API_KEY,
                query = finalQuery
            )

            Log.d(TAG, "PlantApiDataSource.getPlantList Result: ${plantResult.data.size}")

            // Convierte los DTO recibidos desde la API al modelo usado por la app
            val plants = plantResult.data
                .map { plantDto -> plantDto.toPlant() }
                .filter { plant -> plant.id != 0 }

            // Guarda el listado en Room para tener respaldo local
            plantDao.insertPlants(plants.toLocal())

            Log.d(TAG, "API: plantas guardadas en Room: ${plants.size}")

            plants

        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener listado desde API. Buscando en Room...", e)

            // Si falla la API, se intenta recuperar infromación desde Room
            val localPlants = plantDao.getPlants().toExternal()

            // Si había texto de búsqueda, se filtran localmente las plantas guardadas
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

            // Si hay datos locales, se devuelven
            // Si no, se vuelve a lanzar el error original
            if (filteredPlants.isNotEmpty()) {
                filteredPlants
            } else {
                throw e
            }
        }
    }

    override suspend fun getPlantById(id: Int): PlantDetail {
        return try {
            Log.d(TAG, "PlantApiDataSource.getPlantById ID: $id")

            // Consulta el detalle de una planta desde la API usando su ID
            val plantDetail = plantAPI.getPlantById(
                id = id,
                apiKey = API_KEY
            ).toPlantDetail()

            plantDetail

        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener detalle desde API. Buscando en Room...", e)

            // Si falla la API, intenta recuperar el detalle desde las plantas guardadas en MI Jardin
            val savedPlant = plantDao.getSavedPlantById(id)

            if (savedPlant != null) {
                Log.d(TAG, "LOCAL: detalle recuperado desde Mi Jardín: ${savedPlant.commonName}")
                savedPlant.toExternal()
            } else {
                throw e
            }
        }
    }
}