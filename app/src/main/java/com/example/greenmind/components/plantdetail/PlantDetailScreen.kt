package com.example.greenmind.components.plantdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.greenmind.components.commons.PlantUiItemDetail

@Composable
fun PlantDetailScreen(
    plantId: Int,
    modifier: Modifier = Modifier,
    vm: PlantDetailScreenViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(plantId) {
        vm.setPlantId(plantId)
    }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(uiState.errorMessage ?: "")
            }
        }

        uiState.plant != null -> {
            PlantUiItemDetail(
                plant = uiState.plant!!,
                modifier = modifier
            )
        }
    }
}