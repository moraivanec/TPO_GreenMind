package com.example.greenmind.components.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.greenmind.components.Screen

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    vm: LoginScreenViewModel = viewModel(),
    onGoogleLoginClick: () -> Unit
) {
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
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("GreenMind")

        Button(
            onClick = onGoogleLoginClick
        ) {
            Text("Ingresar con Google")
        }
    }
}