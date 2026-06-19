package com.example.greenmind.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("plant_details")
data class PlantDetailLocal(
    @PrimaryKey val id: Int = 0,
    val commonName: String = "",
    val scientificName: String = "",
    val description: String = "",
    val watering: String = "",
    val sunlight: String = "",
    val careLevel: String = "",
    val pruningMonth: String = "",
    val propagation: String = "",
    val cycle: String = "",
    val imageUrl: String = "",
    val dateAdded: Long = System.currentTimeMillis()
)