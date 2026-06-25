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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.greenmind.components.commons.PlantUiItemDetail
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PlantDetailScreen(
    plantId: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: PlantDetailScreenViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(plantId) {
        vm.setPlantId(plantId)
    }

    LaunchedEffect(uiState.savedMessage) {
        uiState.savedMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            vm.clearSavedMessage()
        }
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
                val plant = uiState.plant

                if (plant != null) {
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