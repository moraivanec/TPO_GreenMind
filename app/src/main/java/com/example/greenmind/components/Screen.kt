package com.example.greenmind.components

sealed class Screen(val route: String) {

    object Splash : Screen("splash")

    object Login : Screen("login")

    object PlantList : Screen("plant_list")

    object PlantDetail : Screen("plant_detail")
}