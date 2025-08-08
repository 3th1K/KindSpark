package com.example.kindspark.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import com.example.kindspark.data.preferences.UserPreferencesManager


@Composable
fun KindSparkAppTheme(
    selectedTheme: UserPreferencesManager.AppTheme,
    calmingBackground: Boolean,
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when (selectedTheme) {
        UserPreferencesManager.AppTheme.LIGHT -> {
            if (darkTheme) dynamicDarkColorScheme() else dynamicLightColorScheme()
        }
        UserPreferencesManager.AppTheme.DARK -> {
            dynamicDarkColorScheme()
        }
        UserPreferencesManager.AppTheme.CALM_OCEAN -> {
            createCalmOceanColorScheme(darkTheme)
        }
        UserPreferencesManager.AppTheme.WARM_SUNSET -> {
            createWarmSunsetColorScheme(darkTheme)
        }
        UserPreferencesManager.AppTheme.SERENE_FOREST -> {
            createSereneForestColorScheme(darkTheme)
        }
        UserPreferencesManager.AppTheme.MIDNIGHT_CALM -> {
            createMidnightCalmColorScheme(darkTheme)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KindSparkTypography,
        content = {
            if (calmingBackground) {
                CalmingBackground(selectedTheme, darkTheme) {
                    content()
                }
            } else {
                content()
            }
        }
    )
}

@Composable
private fun dynamicLightColorScheme(): ColorScheme {
    return lightColorScheme(
        primary = Color(0xFF6750A4),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFEADDFF),
        onPrimaryContainer = Color(0xFF21005D),
        secondary = Color(0xFF625B71),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFE8DEF8),
        onSecondaryContainer = Color(0xFF1D192B),
        background = Color(0xFFFFFBFE),
        onBackground = Color(0xFF1C1B1F),
        surface = Color(0xFFFFFBFE),
        onSurface = Color(0xFF1C1B1F)
    )
}

@Composable
private fun dynamicDarkColorScheme(): ColorScheme {
    return darkColorScheme(
        primary = Color(0xFFD0BCFF),
        onPrimary = Color(0xFF381E72),
        primaryContainer = Color(0xFF4F378B),
        onPrimaryContainer = Color(0xFFEADDFF),
        secondary = Color(0xFFCCC2DC),
        onSecondary = Color(0xFF332D41),
        secondaryContainer = Color(0xFF4A4458),
        onSecondaryContainer = Color(0xFFE8DEF8),
        background = Color(0xFF1C1B1F),
        onBackground = Color(0xFFE6E1E5),
        surface = Color(0xFF1C1B1F),
        onSurface = Color(0xFFE6E1E5)
    )
}

private fun createCalmOceanColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        darkColorScheme(
            primary = CalmOceanPrimaryDark,
            onPrimary = CalmOceanAccent,
            primaryContainer = CalmOceanPrimaryDark,
            onPrimaryContainer = CalmOceanAccent,
            secondary = CalmOceanPrimary,
            background = Color(0xFF00251A),
            surface = Color(0xFF00332B),
            onBackground = CalmOceanContainer,
            onSurface = CalmOceanContainer
        )
    } else {
        lightColorScheme(
            primary = CalmOceanPrimaryDark,
            onPrimary = CalmOceanAccent,
            primaryContainer = CalmOceanPrimary,
            onPrimaryContainer = CalmOceanPrimaryDark,
            secondary = CalmOceanPrimary,
            background = CalmOceanContainer,
            surface = CalmOceanAccent,
            onBackground = Color(0xFF00251A),
            onSurface = Color(0xFF00251A)
        )
    }
}

private fun createWarmSunsetColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        darkColorScheme(
            primary = WarmSunsetPrimaryDark,
            onPrimary = WarmSunsetAccent,
            primaryContainer = WarmSunsetPrimaryDark,
            onPrimaryContainer = WarmSunsetAccent,
            secondary = WarmSunsetAccent,
            background = Color(0xFF3E2723),
            surface = Color(0xFF5D4037),
            onBackground = WarmSunsetContainer,
            onSurface = WarmSunsetContainer
        )
    } else {
        lightColorScheme(
            primary = WarmSunsetPrimaryDark,
            onPrimary = Color.White,
            primaryContainer = WarmSunsetPrimary,
            onPrimaryContainer = WarmSunsetPrimaryDark,
            secondary = WarmSunsetAccent,
            background = WarmSunsetSurface,
            surface = Color.White,
            onBackground = Color(0xFF3E2723),
            onSurface = Color(0xFF3E2723)
        )
    }
}

private fun createSereneForestColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        darkColorScheme(
            primary = SereneForestPrimary,
            onPrimary = Color.White,
            primaryContainer = SereneForestPrimaryDark,
            onPrimaryContainer = Color.White,
            secondary = SereneForestContainer,
            background = Color(0xFF1B5E20),
            surface = Color(0xFF2E7D32),
            onBackground = SereneForestContainer,
            onSurface = SereneForestContainer
        )
    } else {
        lightColorScheme(
            primary = SereneForestPrimaryDark,
            onPrimary = Color.White,
            primaryContainer = SereneForestPrimary,
            onPrimaryContainer = SereneForestPrimaryDark,
            secondary = SereneForestContainer,
            background = SereneForestSurface,
            surface = Color.White,
            onBackground = Color(0xFF1B5E20),
            onSurface = Color(0xFF1B5E20)
        )
    }
}

private fun createMidnightCalmColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        darkColorScheme(
            primary = MidnightCalmAccent,
            onPrimary = Color.White,
            primaryContainer = MidnightCalmPrimary,
            onPrimaryContainer = Color.White,
            secondary = MidnightCalmAccent,
            background = MidnightCalmPrimaryDark,
            surface = MidnightCalmSurface,
            onBackground = Color(0xFFD1D1E0),
            onSurface = Color(0xFFD1D1E0)
        )
    } else {
        lightColorScheme(
            primary = MidnightCalmPrimary,
            onPrimary = Color.White,
            primaryContainer = MidnightCalmAccent,
            onPrimaryContainer = MidnightCalmPrimary,
            secondary = MidnightCalmAccent,
            background = Color(0xFFE8EAF6),
            surface = Color.White,
            onBackground = MidnightCalmPrimaryDark,
            onSurface = MidnightCalmPrimaryDark
        )
    }
}

@Composable
private fun CalmingBackground(
    theme: UserPreferencesManager.AppTheme,
    darkMode: Boolean,
    content: @Composable () -> Unit
) {
    val backgroundBrush = when (theme) {
        UserPreferencesManager.AppTheme.CALM_OCEAN -> {
            if (darkMode) {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D1B2A),
                        Color(0xFF1B263B),
                        CalmOceanPrimaryDark
                    )
                )
            } else {
                // Light blue gradient
                Brush.verticalGradient(
                    colors = listOf(
                        CalmOceanContainer,
                        CalmOceanSurface,
                        CalmOceanPrimary.copy(alpha = 0.7f),
                        CalmOceanPrimary
                    )
                )
            }
        }
        UserPreferencesManager.AppTheme.WARM_SUNSET -> {
            if (darkMode) {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3E2723),
                        Color(0xFF5D4037),
                        WarmSunsetPrimaryDark.copy(alpha = 0.6f)
                    )
                )
            } else {
                // Pastel gradient
                Brush.verticalGradient(
                    colors = listOf(
                        WarmSunsetSurface,
                        WarmSunsetContainer,
                        WarmSunsetPrimary,
                        WarmSunsetPrimary.copy(alpha = 0.8f)
                    )
                )
            }
        }
        UserPreferencesManager.AppTheme.SERENE_FOREST -> {
            if (darkMode) {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B5E20),
                        SereneForestPrimaryDark,
                        SereneForestPrimary
                    )
                )
            } else {
                // Blurred green backdrop
                Brush.radialGradient(
                    colors = listOf(
                        SereneForestSurface,
                        SereneForestContainer,
                        SereneForestPrimary.copy(alpha = 0.3f),
                        SereneForestPrimary.copy(alpha = 0.6f)
                    ),
                    radius = 1200f
                )
            }
        }
        UserPreferencesManager.AppTheme.MIDNIGHT_CALM -> {
            if (darkMode) {
                // Starry/smoky gradient
                Brush.verticalGradient(
                    colors = listOf(
                        MidnightCalmPrimaryDark,
                        MidnightCalmSurface,
                        MidnightCalmPrimary,
                        MidnightCalmAccent.copy(alpha = 0.3f)
                    )
                )
            } else {
                // Lighter version for light mode
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8EAF6),
                        MidnightCalmAccent.copy(alpha = 0.2f),
                        MidnightCalmPrimary.copy(alpha = 0.3f),
                        MidnightCalmPrimary.copy(alpha = 0.5f)
                    )
                )
            }
        }
        else -> {
            if (darkMode) {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1C1B1F),
                        Color(0xFF2A2A3E),
                        Color(0xFF3D3D5C),
                        Color(0xFF4A4A6A)
                    )
                )
            } else {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFBFE),
                        Color(0xFFF8F5FF), // Slight lavender tint
                        Color(0xFFF0EDF7), // More noticeable lavender
                        Color(0xFFE8E3F3)  // Soft purple-gray
                    )
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        content()
    }
}
