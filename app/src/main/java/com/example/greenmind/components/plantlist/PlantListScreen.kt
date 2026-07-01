package com.example.greenmind.components.plantlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.greenmind.R
import com.example.greenmind.components.Screen
import com.example.greenmind.components.commons.GreenMindBottomBar
import com.example.greenmind.components.commons.PlantUIList
import com.example.greenmind.ui.theme.GreenMindBackground
import com.example.greenmind.ui.theme.GreenMindHeaderText
import com.example.greenmind.ui.theme.GreenMindInput
import com.example.greenmind.ui.theme.GreenMindLight
import com.example.greenmind.ui.theme.GreenMindPlaceholder
import com.example.greenmind.ui.theme.GreenMindPrimary
import com.example.greenmind.ui.theme.GreenMindSoft
import com.example.greenmind.ui.theme.GreenMindTextDark
import com.example.greenmind.ui.theme.GreenMindTransparent
import com.example.greenmind.ui.theme.GreenMindWhite

@Composable
fun PlantListScreen(
    modifier: Modifier = Modifier,
    vm: PlantListScreenViewModel = hiltViewModel(),
    navController: NavHostController,
    onLogoutClick: () -> Unit
) {
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GreenMindBackground)
    ) {
        // Encabezado principal, con logo, saludo, botón de salir y buscador
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
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(GreenMindSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_greenmind),
                        contentDescription = "Logo GreenMind",
                        modifier = Modifier.size(44.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "GreenMind",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = GreenMindWhite
                    )

                    Text(
                        text = "¡Hola, ${uiState.userName}! Cuida tus plantas",
                        style = MaterialTheme.typography.bodySmall,
                        color = GreenMindHeaderText
                    )
                }

                // El cierre de sesión se maneja desde MainActivity mediante este callback
                Button(
                    onClick = onLogoutClick,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenMindLight,
                        contentColor = GreenMindPrimary
                    )
                ) {
                    Text("Salir")
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = {
                    // Cada cambio del buscador se manda al ViewModel
                    vm.searchChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Busca tu planta favorita...",
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

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 22.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Plantas populares",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GreenMindTextDark
            )

            Spacer(modifier = Modifier.height(10.dp))

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
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = GreenMindPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { vm.fetchPlants() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GreenMindPrimary
                        )
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

        // Barra inferior para navegar entre secciones
        GreenMindBottomBar(
            selectedRoute = Screen.PlantList.route,
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