package com.example.greenmind.components.commons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.greenmind.data.PlantDetail

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlantUiItemDetail(
    plant: PlantDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = plant.commonName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = plant.scientificName,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        GlideImage(
            model = plant.imageUrl,
            contentDescription = plant.commonName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = plant.description,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Riego: ${plant.watering}")
        Text("Luz: ${plant.sunlight}")
        Text("Nivel de cuidado: ${plant.careLevel}")
        Text("Poda: ${plant.pruningMonth}")
        Text("Propagación: ${plant.propagation}")
        Text("Ciclo: ${plant.cycle}")
    }
}