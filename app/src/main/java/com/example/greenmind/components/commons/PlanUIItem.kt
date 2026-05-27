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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.greenmind.data.Plant

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlantUIItem(
    plant: Plant,
    modifier: Modifier = Modifier,
    onPlantClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .clickable { onPlantClick(plant.id) },
        shape = RoundedCornerShape(16.dp),
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
                        .size(width = 88.dp, height = 76.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFDDE8D8)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Sin imagen",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF557B45)
                    )
                }
            } else {
                GlideImage(
                    model = plant.imageUrl,
                    contentDescription = plant.commonName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 88.dp, height = 76.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFDDE8D8))
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = plant.commonName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1F1F1F),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = plant.scientificName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF8A8A8A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                DifficultyBadge(plant.careLevel)
            }
        }
    }
}

@Composable
fun DifficultyBadge(
    careLevel: String
) {
    val badgeColor = when (careLevel.lowercase()) {
        "easy", "fácil", "facil" -> Color(0xFFD9F2DD)
        "medium", "moderate", "moderado" -> Color(0xFFFFF3B8)
        "hard", "difficult", "difícil", "dificil" -> Color(0xFFFFD6D6)
        else -> Color(0xFFE5EEDC)
    }

    val textColor = when (careLevel.lowercase()) {
        "hard", "difficult", "difícil", "dificil" -> Color(0xFFB54848)
        else -> Color(0xFF557B45)
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