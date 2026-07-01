package com.example.greenmind.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GreenMindPrimary,
    secondary = GreenMindSoft,
    tertiary = GreenMindLight,
    background = GreenMindBackground,
    surface = GreenMindWhite,
    onPrimary = GreenMindWhite,
    onSecondary = GreenMindPrimary,
    onTertiary = GreenMindPrimary,
    onBackground = GreenMindTextDark,
    onSurface = GreenMindTextDark
)

private val LightColorScheme = lightColorScheme(
    primary = GreenMindPrimary,
    secondary = GreenMindSoft,
    tertiary = GreenMindLight,
    background = GreenMindBackground,
    surface = GreenMindWhite,
    onPrimary = GreenMindWhite,
    onSecondary = GreenMindPrimary,
    onTertiary = GreenMindPrimary,
    onBackground = GreenMindTextDark,
    onSurface = GreenMindTextDark
)

@Composable
fun GreenMindTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}