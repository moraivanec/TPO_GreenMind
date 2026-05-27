package com.example.greenmind.components.plantlist

import com.example.greenmind.data.Plant

data class PlantListScreenState(
    val userName: String = "Usuario",
    val isLoading: Boolean = false,
    val plantList: List<Plant> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)