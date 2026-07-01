package com.example.greenmind.components.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.greenmind.components.Screen
import com.example.greenmind.ui.theme.GreenMindLight
import com.example.greenmind.ui.theme.GreenMindPrimary
import com.example.greenmind.ui.theme.GreenMindTextBottom
import com.example.greenmind.ui.theme.GreenMindTransparent
import com.example.greenmind.ui.theme.GreenMindWhite

@Composable
fun GreenMindBottomBar(
    selectedRoute: String,
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMiJardinClick: () -> Unit,
    onChatClick: () -> Unit
) {
    // Barra inferior reutilizable para navegar entre las secciones principales
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(GreenMindWhite)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(
            icon = "⌂",
            label = "Inicio",
            isSelected = selectedRoute == Screen.PlantList.route,
            onClick = onHomeClick
        )

        BottomBarItem(
            icon = "⌕",
            label = "Buscar",
            isSelected = selectedRoute == Screen.Buscar.route,
            onClick = onSearchClick
        )

        BottomBarItem(
            icon = "🌱",
            label = "Mi Jardín",
            isSelected = selectedRoute == Screen.MiJardin.route,
            onClick = onMiJardinClick
        )

        BottomBarItem(
            icon = "🗨",
            label = "Chat IA",
            isSelected = selectedRoute == Screen.ChatIA.route,
            onClick = onChatClick
        )
    }
}

@Composable
private fun BottomBarItem(
    icon: String,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // Cambia el color del ítem si corresponde a la pantalla actual
    val contentColor = if (isSelected) GreenMindPrimary else GreenMindTextBottom
    val backgroundColor = if (isSelected) GreenMindLight else GreenMindTransparent

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(backgroundColor)
            .clickable {
                onClick()
            }
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.titleMedium,
            color = contentColor
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = contentColor
        )
    }
}