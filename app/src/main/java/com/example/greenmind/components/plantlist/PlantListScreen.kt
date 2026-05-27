package com.example.greenmind.components.plantlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.greenmind.components.Screen
import com.example.greenmind.components.commons.PlantUIList

@Composable
fun PlantListScreen(
    modifier: Modifier = Modifier,
    vm: PlantListScreenViewModel = viewModel(),
    navController: NavHostController,
    onLogoutClick: () -> Unit
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Bienvenida ${uiState.userName}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onLogoutClick
            ) {
                Text("Salir")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Listado de plantas",
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.searchQuery,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Buscar planta") },
            singleLine = true,
            onValueChange = {
                vm.searchChange(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.errorMessage != null -> {
                Text(uiState.errorMessage ?: "")

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { vm.fetchPlants() }
                ) {
                    Text("Reintentar")
                }
            }

            else -> {
                PlantUIList(
                    plantList = uiState.plantList,
                    modifier = Modifier.fillMaxSize(),
                    onPlantClick = { id ->
                        navController.navigate(
                            Screen.PlantDetail.route + "/$id"
                        )
                    }
                )
            }
        }
    }
}