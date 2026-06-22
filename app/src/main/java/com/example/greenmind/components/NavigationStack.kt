package com.example.greenmind.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.greenmind.components.buscar.BuscarScreen
import com.example.greenmind.components.login.LoginScreen
import com.example.greenmind.components.mijardin.MiJardinScreen
import com.example.greenmind.components.plantdetail.PlantDetailScreen
import com.example.greenmind.components.plantlist.PlantListScreen
import com.example.greenmind.components.splash.SplashScreen
import com.example.greenmind.components.chatia.ChatIAScreen

@Composable
fun NavigationStack(
    navController: NavHostController = rememberNavController(),
    onGoogleLoginClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                onGoogleLoginClick = onGoogleLoginClick
            )
        }

        composable(Screen.PlantList.route) {
            PlantListScreen(
                navController = navController,
                onLogoutClick = onLogoutClick
            )
        }

        composable(Screen.Buscar.route) {
            BuscarScreen(
                navController = navController
            )
        }

        composable(Screen.MiJardin.route) {
            MiJardinScreen(
                navController = navController
            )
        }

        composable(Screen.PlantDetail.route + "/{plantId}") {
            val stringId = it.arguments?.getString("plantId")
            val id = stringId?.toInt() ?: 0

            PlantDetailScreen(
                plantId = id,
                navController = navController
            )
        }

        composable(Screen.ChatIA.route) {
            ChatIAScreen(
                navController = navController
            )
        }
    }
}
