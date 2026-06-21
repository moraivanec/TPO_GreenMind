package com.example.greenmind.components.buscar

import com.example.greenmind.data.Plant

data class BuscarScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val plantList: List<Plant> = emptyList(),
    val errorMessage: String? = null
)