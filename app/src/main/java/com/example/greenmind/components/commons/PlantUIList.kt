package com.example.greenmind.components.commons

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.greenmind.data.Plant

@Composable
fun PlantUIList(
    plantList: List<Plant>,
    modifier: Modifier = Modifier,
    onPlantClick: (Int) -> Unit
) {
    // LazyColumn muestra una lista eficiente
    // Solo compone los ítems visibles en pantalla
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = plantList,
            key = { plant -> plant.id }
        ) { plant ->
            PlantUIItem(
                plant = plant,
                onPlantClick = onPlantClick
            )
        }
    }
}