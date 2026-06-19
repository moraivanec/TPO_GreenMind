package com.example.greenmind.components.plantdetail

import com.example.greenmind.data.PlantDetail

data class PlantDetailScreenState(
    val id: Int = 0,
    val isLoading: Boolean = false,
    val plant: PlantDetail? = null,
    val errorMessage: String? = null,
    val savedMessage: String? = null,
    val isSaved: Boolean = false
)