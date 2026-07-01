package com.example.greenmind.components.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.greenmind.data.Plant
import com.example.greenmind.ui.theme.GreenMindBadgeDefault
import com.example.greenmind.ui.theme.GreenMindHardBadge
import com.example.greenmind.ui.theme.GreenMindHardText
import com.example.greenmind.ui.theme.GreenMindMediumBadge
import com.example.greenmind.ui.theme.GreenMindPrimary
import com.example.greenmind.ui.theme.GreenMindSoft
import com.example.greenmind.ui.theme.GreenMindSuccessSoft
import com.example.greenmind.ui.theme.GreenMindTextBlack
import com.example.greenmind.ui.theme.GreenMindTextGray
import com.example.greenmind.ui.theme.GreenMindWhite

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlantUIItem(
    plant: Plant,
    modifier: Modifier = Modifier,
    onPlantClick: (Int) -> Unit
) {
    // Tarjeta individual que representa una planta dentro de una lista
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .clickable { onPlantClick(plant.id) },
        shape = RoundedCornerShape(16.dp),
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
                        .size(width = 88.dp, height = 76.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(GreenMindSoft),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin imagen",
                        style = MaterialTheme.typography.bodySmall,
                        color = GreenMindPrimary
                    )
                }
            } else {
                // Glide carga la imagen de la planta desde una URL
                GlideImage(
                    model = plant.imageUrl,
                    contentDescription = plant.commonName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 88.dp, height = 76.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(GreenMindSoft)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = plant.commonName,
                    style = MaterialTheme.typography.titleMedium,
                    color = GreenMindTextBlack,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = plant.scientificName,
                    style = MaterialTheme.typography.bodySmall,
                    color = GreenMindTextGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Badge visual que muestra el nivel de cuidado de la planta
                DifficultyBadge(plant.careLevel)
            }
        }
    }
}

@Composable
fun DifficultyBadge(
    careLevel: String
) {
    // Cambia el color del badge según el nivel de cuidado recibido
    val badgeColor = when (careLevel.lowercase()) {
        "easy", "fácil", "facil" -> GreenMindSuccessSoft
        "medium", "moderate", "moderado" -> GreenMindMediumBadge
        "hard", "difficult", "difícil", "dificil" -> GreenMindHardBadge
        else -> GreenMindBadgeDefault
    }

    val textColor = when (careLevel.lowercase()) {
        "hard", "difficult", "difícil", "dificil" -> GreenMindHardText
        else -> GreenMindPrimary
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(badgeColor)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = careLevel.ifBlank { "No informado" },
            style = MaterialTheme.typography.bodySmall,
            color = textColor
        )
    }
}