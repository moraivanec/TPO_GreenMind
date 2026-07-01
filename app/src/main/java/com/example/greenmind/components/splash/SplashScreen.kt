package com.example.greenmind.components.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.greenmind.R
import com.example.greenmind.components.Screen
import com.example.greenmind.ui.theme.GreenMindBackground
import com.example.greenmind.ui.theme.GreenMindPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    vm: SplashScreenViewModel = hiltViewModel()
) {
    // Ejecuta la lógica inicial una sola vez cuando se muestra en pantalla
    LaunchedEffect(Unit) {
        delay(2000)

        // Si ya existe una sesión activa, navega directamente al Inicio de la app
        if (vm.isUserLogged()) {
            navController.navigate(Screen.PlantList.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        } else {
            // Si no hay sesión iniciada, navega al Login
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GreenMindBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_greenmind),
                contentDescription = "Logo GreenMind",
                modifier = Modifier.size(130.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "GreenMind",
                style = MaterialTheme.typography.headlineMedium,
                color = GreenMindPrimary
            )
        }
    }
}