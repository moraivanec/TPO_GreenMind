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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.greenmind.R
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
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = 34.dp,
                        bottomEnd = 34.dp
                    )
                )
                .background(Color(0xFF557B45))
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDDE8D8)),
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
                        color = Color.White
                    )

                    Text(
                        text = "¡Hola, ${uiState.userName}! Cuida tus plantas",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE8F1E4)
                    )
                }

                Button(
                    onClick = onLogoutClick,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEAF3E4),
                        contentColor = Color(0xFF557B45)
                    )
                ) {
                    Text("Salir")
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = {
                    vm.searchChange(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Busca tu planta favorita...",
                        color = Color(0xFFEEF4EA)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF9DB892),
                    unfocusedContainerColor = Color(0xFF9DB892),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Plantas populares",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            )

            Spacer(modifier = Modifier.height(10.dp))

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
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = Color(0xFF557B45)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { vm.fetchPlants() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF557B45)
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
    }
}