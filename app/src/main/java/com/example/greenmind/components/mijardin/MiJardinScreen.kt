package com.example.greenmind.components.mijardin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.greenmind.components.Screen
import com.example.greenmind.components.commons.GreenMindBottomBar
import com.example.greenmind.data.PlantDetail
import com.example.greenmind.ui.theme.GreenMindBackground
import com.example.greenmind.ui.theme.GreenMindError
import com.example.greenmind.ui.theme.GreenMindLight
import com.example.greenmind.ui.theme.GreenMindPrimary
import com.example.greenmind.ui.theme.GreenMindSoft
import com.example.greenmind.ui.theme.GreenMindTextDark
import com.example.greenmind.ui.theme.GreenMindTextGray
import com.example.greenmind.ui.theme.GreenMindTextSecondary
import com.example.greenmind.ui.theme.GreenMindWhite

@Composable
fun MiJardinScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: MiJardinScreenViewModel = hiltViewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    // Carga las plantas guardadas una sola vez cuando se abre la pantalla
    LaunchedEffect(Unit) {
        vm.loadSavedPlants()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GreenMindBackground)
    ) {
        // Encabezado de Mi Jardin con la cantidad de plantas guardadas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp)
                .background(
                    color = GreenMindPrimary,
                    shape = RoundedCornerShape(
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
                .padding(horizontal = 22.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = "Mi Jardín",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = GreenMindWhite
                )

                Text(
                    text = "${uiState.savedPlants.size} plantas guardadas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreenMindLight
                )
            }
        }

        Box(
            modifier = Modifier.weight(1f)
        ) {
            // Muestra distintos contenidos según el estado actual de la pantalla
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

                uiState.savedPlants.isEmpty() -> {
                    EmptyMiJardinState(
                        onExploreClick = {
                            navController.navigate(Screen.PlantList.route)
                        }
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 18.dp, vertical = 16.dp)
                    ) {
                        items(
                            items = uiState.savedPlants,
                            key = { plant -> plant.id }
                        ) { plant ->
                            SavedPlantItem(
                                plant = plant,
                                onClick = {
                                    navController.navigate("${Screen.PlantDetail.route}/${plant.id}")
                                },
                                onRemoveClick = {
                                    vm.removePlant(plant.id)
                                }
                            )
                        }
                    }
                }
            }
        }

        // Barra inferior para navegar entre las secciones principales
        GreenMindBottomBar(
            selectedRoute = Screen.MiJardin.route,
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
fun EmptyMiJardinState(
    onExploreClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tu jardín está vacío",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = GreenMindPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Guardá tus plantas favoritas para verlas acá.",
                style = MaterialTheme.typography.bodyMedium,
                color = GreenMindTextSecondary
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onExploreClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenMindPrimary
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Explorar plantas",
                    color = GreenMindWhite
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SavedPlantItem(
    plant: PlantDetail,
    onClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreenMindWhite
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Si la planta no tiene imagen, se muestra un placeholder
            if (plant.imageUrl.isBlank()) {
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(GreenMindSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin imagen",
                        color = GreenMindPrimary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                // Glide carga la imagen de la planta desde su URL
                GlideImage(
                    model = plant.imageUrl,
                    contentDescription = plant.commonName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(82.dp)
                        .clip(RoundedCornerShape(14.dp))
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = plant.commonName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GreenMindTextDark
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = plant.scientificName,
                    style = MaterialTheme.typography.bodySmall,
                    color = GreenMindTextGray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nivel: ${plant.careLevel}",
                    style = MaterialTheme.typography.bodySmall,
                    color = GreenMindPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Eliminar",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = GreenMindError,
                    modifier = Modifier.clickable {
                        onRemoveClick()
                    }
                )
            }
        }
    }
}