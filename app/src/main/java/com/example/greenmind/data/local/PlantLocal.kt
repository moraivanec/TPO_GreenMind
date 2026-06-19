package com.example.greenmind.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("plants")
data class PlantLocal(
    @PrimaryKey val id: Int = 0,
    val commonName: String = "",
    val scientificName: String = "",
    val careLevel: String = "",
    val imageUrl: String = ""
)