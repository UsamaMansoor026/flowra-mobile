package com.example.flowra.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flowra.MainViewModel
import com.example.flowra.ui.home.HomeScreen
import com.example.flowra.ui.insignts.InsightsScreen
import com.example.flowra.ui.recents.RecentsScreen
import com.example.flowra.ui.recurring.RecurringScreen
import com.example.flowra.ui.settings.SettingScreen
import com.example.flowra.ui.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun FlowraNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigationToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            val viewModel: MainViewModel = koinViewModel()
            HomeScreen(navController, viewModel)
        }
        composable(Screen.Insights.route)  { InsightsScreen(navController) }
        composable(Screen.Recurring.route) {
            val viewModel: MainViewModel = koinViewModel()
            RecurringScreen(navController, viewModel)
        }
        composable(Screen.Setting.route)   { SettingScreen(navController) }
        composable(Screen.Recents.route) {
            val viewModel: MainViewModel = koinViewModel()
            RecentsScreen(navController, viewModel)
        }
    }
}