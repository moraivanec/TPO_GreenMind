package com.example.greenmind.components.buscar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.greenmind.components.Screen
import com.example.greenmind.components.commons.GreenMindBottomBar
import com.example.greenmind.components.commons.PlantUIList
import com.example.greenmind.ui.theme.GreenMindBackground
import com.example.greenmind.ui.theme.GreenMindInput
import com.example.greenmind.ui.theme.GreenMindPlaceholder
import com.example.greenmind.ui.theme.GreenMindPrimary
import com.example.greenmind.ui.theme.GreenMindSearchSoft
import com.example.greenmind.ui.theme.GreenMindTransparent
import com.example.greenmind.ui.theme.GreenMindWhite

@Composable
fun BuscarScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: BuscarScreenViewModel = hiltViewModel()
) {
    // Observa el estado del ViewModel respetando el ciclo de vida de la pantalla
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GreenMindBackground)
    ) {
        // Encabezado de la pantalla con título y campo de búsqueda
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 34.dp,
                        bottomEnd = 34.dp
                    )
                )
                .background(GreenMindPrimary)
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            Text(
                text = "‹  Buscar plantas",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = GreenMindWhite,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = {
                    // Cada cambio en el texto se manda al ViewModel para hacer la búsqueda
                    vm.searchChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Busca por nombre...",
                        color = GreenMindPlaceholder
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = GreenMindInput,
                    unfocusedContainerColor = GreenMindInput,
                    focusedBorderColor = GreenMindTransparent,
                    unfocusedBorderColor = GreenMindTransparent,
                    focusedTextColor = GreenMindWhite,
                    unfocusedTextColor = GreenMindWhite,
                    cursorColor = GreenMindWhite
                )
            )
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            // La pantalla cambia su contenido según el estado actual de la búsqueda
            when {
                uiState.searchQuery.isBlank() -> {
                    EmptySearchState()
                }

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

                uiState.plantList.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No se encontraron plantas",
                            color = GreenMindPrimary
                        )
                    }
                }

                else -> {
                    PlantUIList(
                        plantList = uiState.plantList,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 22.dp, vertical = 18.dp),
                        onPlantClick = { id ->
                            navController.navigate("${Screen.PlantDetail.route}/$id")
                        }
                    )
                }
            }
        }

        // Barra inferior con la que se navega en las secciones principales
        GreenMindBottomBar(
            selectedRoute = Screen.Buscar.route,
            onHomeClick = {
                navController.navigate(Screen.PlantList.route) {
                    launchSingleTop = true
                }
            },
            onSearchClick = {
                navController.navigate(Screen.Buscar.route) {
                    launchSingleTop = true
                }
            },
            onMiJardinClick = {
                navController.navigate(Screen.MiJardin.route) {
                    launchSingleTop = true
                }
            },
            onChatClick = {
                navController.navigate(Screen.ChatIA.route) {
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun EmptySearchState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .clip(CircleShape)
                    .background(GreenMindSearchSoft),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "⌕",
                    style = MaterialTheme.typography.headlineLarge,
                    color = GreenMindPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Escriba el nombre de una planta",
                style = MaterialTheme.typography.bodyMedium,
                color = GreenMindPrimary
            )

            Text(
                text = "para comenzar",
                style = MaterialTheme.typography.bodyMedium,
                color = GreenMindPrimary
            )
        }
    }
}