package com.example.greenmind.components.plantdetail

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.greenmind.components.commons.PlantUiItemDetail
import com.example.greenmind.ui.theme.GreenMindBackground
import com.example.greenmind.ui.theme.GreenMindPrimary

@Composable
fun PlantDetailScreen(
    plantId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: PlantDetailScreenViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Cada vez que cambia el ID de la planta, se carga su detalle
    LaunchedEffect(plantId) {
        vm.setPlantId(plantId)
    }

    // Muestra un Toast cuando el viewmodel informa que la planta fue guardada o eliminada
    LaunchedEffect(uiState.savedMessage) {
        uiState.savedMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            vm.clearSavedMessage()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GreenMindBackground)
    ) {
        // Muestra carga, error o detalle según el estado actual
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = GreenMindPrimary
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
                        color = GreenMindPrimary
                    )
                }
            }

            uiState.plant != null -> {
                uiState.plant?.let { plant ->
                    PlantUiItemDetail(
                        plant = plant,
                        isSaved = uiState.isSaved,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onSaveClick = {
                            vm.togglePlantInGarden()
                        }
                    )
                }
            }
        }
    }
}