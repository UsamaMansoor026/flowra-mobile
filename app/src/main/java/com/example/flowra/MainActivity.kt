package com.example.flowra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.example.flowra.ui.components.AppBottomBar
import com.example.flowra.ui.components.AppTopBar
import com.example.flowra.ui.navigation.FlowraNavHost
import com.example.flowra.ui.navigation.Screen
import com.example.flowra.ui.theme.FlowraTheme
import com.example.flowra.ui.theme.PrimaryBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            FlowraTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = currentRoute in setOf(
                    Screen.Home.route,
                    Screen.Insights.route,
                    Screen.Recurring.route,
                    Screen.Setting.route
                )
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PrimaryBackground),
                    containerColor = PrimaryBackground,
                    topBar = {
                        if(showBottomBar) {
                            AppTopBar(currentRoute)
                        }
                    },
                    bottomBar = {
                        if (showBottomBar) {
                            AppBottomBar(navController)
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        FlowraNavHost(navController = navController)
                    }
                }
            }
        }
    }
}