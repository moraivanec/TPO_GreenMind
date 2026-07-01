package com.example.greenmind.components.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.greenmind.R
import com.example.greenmind.components.Screen
import com.example.greenmind.ui.theme.GreenMindBackground
import com.example.greenmind.ui.theme.GreenMindPrimary
import com.example.greenmind.ui.theme.GreenMindTextDark
import com.example.greenmind.ui.theme.GreenMindTextDisabled
import com.example.greenmind.ui.theme.GreenMindTextLightGray
import com.example.greenmind.ui.theme.GreenMindWhite

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    vm: LoginScreenViewModel = hiltViewModel(),
    onGoogleLoginClick: () -> Unit
) {
    // Escucha eventos enviados por el ViewModel, como navegar después de iniciar sesión
    LaunchedEffect(Unit) {
        vm.uiEvent.collect {
            navController.navigate(Screen.PlantList.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GreenMindBackground)
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_greenmind),
            contentDescription = "Logo GreenMind",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "GreenMind",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = GreenMindPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Cuida tus plantas como un experto",
            style = MaterialTheme.typography.bodyMedium,
            color = GreenMindTextLightGray
        )

        Spacer(modifier = Modifier.height(48.dp))

        LoginFeatureItem(
            title = "Aprende a cuidar tus plantas",
            subtitle = "Descubre consejos personalizados para cada tipo de planta"
        )

        Spacer(modifier = Modifier.height(28.dp))

        LoginFeatureItem(
            title = "Crea tu jardín virtual",
            subtitle = "Guarda y organiza tus plantas favoritas en un sólo lugar"
        )

        Spacer(modifier = Modifier.height(28.dp))

        LoginFeatureItem(
            title = "Asistente inteligente",
            subtitle = "Pregunta lo que necesites sobre jardinería"
        )

        Spacer(modifier = Modifier.weight(1f))

        // El inicio de sesión con Google se maneja desde MainActivity mediante este callback
        Button(
            onClick = onGoogleLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreenMindPrimary
            )
        ) {
            Text(
                text = "Iniciar sesión con Google",
                color = GreenMindWhite,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(34.dp))

        Text(
            text = "Al continuar, aceptas nuestros términos y condiciones",
            style = MaterialTheme.typography.bodySmall,
            color = GreenMindTextDisabled
        )

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
fun LoginFeatureItem(
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_greenmind),
            contentDescription = null,
            modifier = Modifier.size(46.dp)
        )

        Column(
            modifier = Modifier.padding(start = 14.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = GreenMindTextDark
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = GreenMindTextLightGray
            )
        }
    }
}