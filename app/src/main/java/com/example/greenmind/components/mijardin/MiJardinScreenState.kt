package com.example.greenmind.components.mijardin

import com.example.greenmind.data.PlantDetail

data class MiJardinScreenState(
    val isLoading: Boolean = false,
    val savedPlants: List<PlantDetail> = emptyList(),
    val errorMessage: String? = null
)