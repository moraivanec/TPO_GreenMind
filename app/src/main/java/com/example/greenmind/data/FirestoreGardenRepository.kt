package com.example.greenmind.data

import com.example.greenmind.domain.IGardenRemoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreGardenRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : IGardenRemoteRepository {

    // Obtiene el ID del usuario autenticado
    // Si no hay usuario logueado, lanza una excepción
    private fun getUserId(): String {
        return auth.currentUser?.uid
            ?: throw Exception("No hay usuario autenticado")
    }

    // Devuelve la colección remota donde se guardan las plantas del usuario actual
    private fun getGardenCollection() =
        firestore
            .collection("users")
            .document(getUserId())
            .collection("garden")

    // Guarda o actualiza una planta en FireStore dentro del jardín del usuario
    override suspend fun savePlant(plantDetail: PlantDetail) {
        val plantData = hashMapOf(
            "id" to plantDetail.id,
            "commonName" to plantDetail.commonName,
            "scientificName" to plantDetail.scientificName,
            "description" to plantDetail.description,
            "watering" to plantDetail.watering,
            "sunlight" to plantDetail.sunlight,
            "careLevel" to plantDetail.careLevel,
            "pruningMonth" to plantDetail.pruningMonth,
            "propagation" to plantDetail.propagation,
            "cycle" to plantDetail.cycle,
            "imageUrl" to plantDetail.imageUrl,
            "updatedAt" to System.currentTimeMillis()
        )

        getGardenCollection()
            .document(plantDetail.id.toString())
            .set(plantData)
            .await()
    }

    override suspend fun removePlant(id: Int) {
        getGardenCollection()
            .document(id.toString())
            .delete()
            .await()
    }

    // Obtiene desde FireStore todas las plantas guardadas por el usuario
    override suspend fun getSavedPlants(): List<PlantDetail> {
        val snapshot = getGardenCollection()
            .get()
            .await()

        return snapshot.documents.mapNotNull { document ->
            val id = document.getLong("id")?.toInt()
                ?: document.id.toIntOrNull()
                ?: return@mapNotNull null

            PlantDetail(
                id = id,
                commonName = document.getString("commonName") ?: "Sin nombre",
                scientificName = document.getString("scientificName") ?: "Sin nombre científico",
                description = document.getString("description") ?: "No hay descripción disponible.",
                watering = document.getString("watering") ?: "No informado",
                sunlight = document.getString("sunlight") ?: "No informado",
                careLevel = document.getString("careLevel") ?: "No informado",
                pruningMonth = document.getString("pruningMonth") ?: "No informado",
                propagation = document.getString("propagation") ?: "No informado",
                cycle = document.getString("cycle") ?: "No informado",
                imageUrl = document.getString("imageUrl") ?: ""
            )
        }
    }
}