package com.example.greenmind.components.commons

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.greenmind.data.PlantDetail

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlantUiItemDetail(
    plant: PlantDetail,
    isSaved: Boolean,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF4F6F1))
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        ) {
            if (plant.imageUrl.isBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFDDE8D8)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin imagen disponible",
                        color = Color(0xFF557B45)
                    )
                }
            } else {
                GlideImage(
                    model = plant.imageUrl,
                    contentDescription = plant.commonName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .padding(18.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        onBackClick()
                    }
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "‹",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF557B45)
                )
            }

            Box(
                modifier = Modifier
                    .padding(18.dp)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        onSaveClick()
                    }
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isSaved) "♥" else "♡",
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isSaved) Color(0xFFD71920) else Color(0xFF557B45)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = plant.commonName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = plant.scientificName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF8A8A8A)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFD9F2DD))
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Nivel: ${plant.careLevel}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF557B45)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = plant.description,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 20.sp,
                        color = Color(0xFF4A4A4A)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            DetailCard(
                title = "Riego",
                text = plant.watering
            )

            DetailCard(
                title = "Luz",
                text = plant.sunlight
            )

            DetailCard(
                title = "Cuidados especiales",
                text = "Nivel de cuidado: ${plant.careLevel}"
            )

            DetailCard(
                title = "Consejos útiles",
                text = "Poda: ${plant.pruningMonth}\nPropagación: ${plant.propagation}\nCiclo: ${plant.cycle}"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    onSaveClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSaved) Color(0xFFDDE8D8) else Color(0xFF557B45)
                )
            ) {
                Text(
                    text = if (isSaved) "♡ Quitar de Mi Jardín" else "♡ Guardar en Mi Jardín",
                    color = if (isSaved) Color(0xFF557B45) else Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DetailCard(
    title: String,
    text: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2A2A2A)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF4A4A4A),
                lineHeight = 18.sp
            )
        }
    }
}

