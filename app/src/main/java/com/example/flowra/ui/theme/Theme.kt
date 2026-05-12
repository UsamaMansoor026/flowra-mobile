package com.example.flowra.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val FlowraColorScheme = darkColorScheme(
    primary = CyanGlow,
    secondary = IndigoGlow,
    background = PrimaryBackground,
    surface = SecondarySurface,
    onBackground = PrimaryText,
    onSurface = PrimaryText,
    error = ErrorRed
)

@Composable
fun FlowraTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = PrimaryBackground.toArgb()
            window.navigationBarColor = PrimaryBackground.toArgb()

            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = FlowraColorScheme,
        typography = Typography,
        content = content
    )
}