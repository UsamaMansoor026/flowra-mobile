package com.example.flowra.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Insights : Screen("insights")
    object Recurring : Screen("recurring")
    object Setting : Screen("setting")
}