package com.example.greenmind

import com.example.greenmind.data.Plant
import com.example.greenmind.data.PlantDetail
import com.example.greenmind.domain.IPlantRepository

class FakePlantRepository(
    private var plantList: List<Plant> = emptyList(),
    private var savedPlants: MutableList<PlantDetail> = mutableListOf(),
    private val throwError: Boolean = false
) : IPlantRepository {

    override suspend fun fetchPlants(query: String): List<Plant> {
        if (throwError) throw Exception("Error simulado")

        return if (query.isBlank()) {
            plantList
        } else {
            plantList.filter { plant ->
                plant.commonName.contains(query, ignoreCase = true) ||
                        plant.scientificName.contains(query, ignoreCase = true) ||
                        plant.careLevel.contains(query, ignoreCase = true)
            }
        }
    }

    override suspend fun fetchPlantDetail(id: Int): PlantDetail {
        if (throwError) throw Exception("Error simulado")

        return savedPlants.find { it.id == id }
            ?: PlantDetail(
                id = id,
                commonName = "Planta $id",
                scientificName = "Scientific $id",
                description = "Descripción de prueba",
                watering = "Moderado",
                sunlight = "Sol indirecto",
                careLevel = "Easy",
                pruningMonth = "No informado",
                propagation = "No informado",
                cycle = "Perenne",
                imageUrl = ""
            )
    }

    override suspend fun savePlantInGarden(plantDetail: PlantDetail) {
        if (throwError) throw Exception("Error simulado")

        savedPlants.removeAll { it.id == plantDetail.id }
        savedPlants.add(plantDetail)
    }

    override suspend fun removePlantFromGarden(id: Int) {
        if (throwError) throw Exception("Error simulado")

        savedPlants.removeAll { it.id == id }
    }

    override suspend fun isPlantSaved(id: Int): Boolean {
        if (throwError) throw Exception("Error simulado")

        return savedPlants.any { it.id == id }
    }

    override suspend fun getSavedPlants(): List<PlantDetail> {
        if (throwError) throw Exception("Error simulado")

        return savedPlants
    }

    override suspend fun syncGardenFromRemote() {
        if (throwError) throw Exception("Error simulado")
    }
}