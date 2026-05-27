package com.example.greenmind.components.plantdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.greenmind.components.commons.PlantUiItemDetail

@Composable
fun PlantDetailScreen(
    plantId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: PlantDetailScreenViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(plantId) {
        vm.setPlantId(plantId)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F1))
    ) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF557B45)
                    )
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = Color(0xFF557B45)
                    )
                }
            }

            uiState.plant != null -> {
                PlantUiItemDetail(
                    plant = uiState.plant!!,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}