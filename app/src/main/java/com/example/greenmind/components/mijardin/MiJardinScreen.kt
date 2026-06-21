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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.greenmind.components.Screen
import com.example.greenmind.components.plantlist.GreenMindBottomBar
import com.example.greenmind.data.PlantDetail

@Composable
fun MiJardinScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: MiJardinScreenViewModel = viewModel()
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.loadSavedPlants()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F1))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp)
                .background(
                    color = Color(0xFF557B45),
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
                    color = Color.White
                )

                Text(
                    text = "${uiState.savedPlants.size} plantas guardadas",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFEAF3E4)
                )
            }
        }

        Box(
            modifier = Modifier.weight(1f)
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
                // Más adelante va Chat IA
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
                color = Color(0xFF557B45)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Guardá tus plantas favoritas para verlas acá.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6F6F6F)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onExploreClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF557B45)
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Explorar plantas",
                    color = Color.White
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
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (plant.imageUrl.isBlank()) {
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFDDE8D8)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin imagen",
                        color = Color(0xFF557B45),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
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
                    color = Color(0xFF222222)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = plant.scientificName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF8A8A8A)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Nivel: ${plant.careLevel}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF557B45)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Eliminar",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD71920),
                    modifier = Modifier.clickable {
                        onRemoveClick()
                    }
                )
            }
        }
    }
}