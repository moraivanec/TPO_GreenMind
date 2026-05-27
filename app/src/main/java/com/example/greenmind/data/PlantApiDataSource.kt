package com.example.greenmind.data

import android.util.Log

class PlantApiDataSource(
    private val plantAPI: IPlantAPI = RetrofitInstance.plantAPI
) : IPlantDataSource {

    private val TAG = "PLANT-API"

    companion object {
        private const val API_KEY = "sk-46Ft6a1614babec2f17640"
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

            plantResult.data
                .map { plantDto -> plantDto.toPlant() }
                .filter { plant -> plant.id != 0 }

        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener listado de plantas: ${e.localizedMessage}", e)
            throw e
        }
    }

    override suspend fun getPlantById(id: Int): PlantDetail {
        return try {
            Log.d(TAG, "PlantApiDataSource.getPlantById ID: $id")

            plantAPI.getPlantById(
                id = id,
                apiKey = API_KEY
            ).toPlantDetail()

        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener detalle de planta: ${e.localizedMessage}", e)
            throw e
        }
    }
}