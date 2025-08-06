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

// Custom theme colors for different themes
object KindSparkTheme {
    
    // Soft Blue Theme Colors
    val SoftBlue = Color(0xFF87CEEB)
    val SoftBlueDark = Color(0xFF4682B4)
    val SoftBlueContainer = Color(0xFFE6F3FF)
    
    // Soft Green Theme Colors
    val SoftGreen = Color(0xFF90EE90)
    val SoftGreenDark = Color(0xFF32CD6B)
    val SoftGreenContainer = Color(0xFFE6FFE6)
}

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
        UserPreferencesManager.AppTheme.SOFT_BLUE -> {
            createSoftBlueColorScheme(darkTheme)
        }
        UserPreferencesManager.AppTheme.SOFT_GREEN -> {
            createSoftGreenColorScheme(darkTheme)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
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

private fun createSoftBlueColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        darkColorScheme(
            primary = KindSparkTheme.SoftBlue,
            onPrimary = Color.White,
            primaryContainer = KindSparkTheme.SoftBlueDark,
            onPrimaryContainer = Color.White,
            secondary = KindSparkTheme.SoftBlue.copy(alpha = 0.7f),
            background = Color(0xFF0D1B2A),
            surface = Color(0xFF1B263B),
            onBackground = Color(0xFFE6F3FF),
            onSurface = Color(0xFFE6F3FF)
        )
    } else {
        lightColorScheme(
            primary = KindSparkTheme.SoftBlueDark,
            onPrimary = Color.White,
            primaryContainer = KindSparkTheme.SoftBlueContainer,
            onPrimaryContainer = KindSparkTheme.SoftBlueDark,
            secondary = KindSparkTheme.SoftBlue,
            background = Color(0xFFF8FCFF),
            surface = Color.White,
            onBackground = Color(0xFF0D1B2A),
            onSurface = Color(0xFF0D1B2A)
        )
    }
}

private fun createSoftGreenColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        darkColorScheme(
            primary = KindSparkTheme.SoftGreen,
            onPrimary = Color.Black,
            primaryContainer = KindSparkTheme.SoftGreenDark,
            onPrimaryContainer = Color.White,
            secondary = KindSparkTheme.SoftGreen.copy(alpha = 0.7f),
            background = Color(0xFF0A1F0A),
            surface = Color(0xFF1B2F1B),
            onBackground = Color(0xFFE6FFE6),
            onSurface = Color(0xFFE6FFE6)
        )
    } else {
        lightColorScheme(
            primary = KindSparkTheme.SoftGreenDark,
            onPrimary = Color.White,
            primaryContainer = KindSparkTheme.SoftGreenContainer,
            onPrimaryContainer = KindSparkTheme.SoftGreenDark,
            secondary = KindSparkTheme.SoftGreen,
            background = Color(0xFFF8FFF8),
            surface = Color.White,
            onBackground = Color(0xFF0A1F0A),
            onSurface = Color(0xFF0A1F0A)
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
        UserPreferencesManager.AppTheme.SOFT_BLUE -> {
            if (darkMode) {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D1B2A),
                        Color(0xFF1B263B),
                        Color(0xFF415A77)
                    )
                )
            } else {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6F3FF),
                        Color(0xFFCCE7FF),
                        Color(0xFFB3DBFF)
                    )
                )
            }
        }
        UserPreferencesManager.AppTheme.SOFT_GREEN -> {
            if (darkMode) {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0A1F0A),
                        Color(0xFF1B2F1B),
                        Color(0xFF2D4A2D)
                    )
                )
            } else {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE6FFE6),
                        Color(0xFFCCFFCC),
                        Color(0xFFB3FFB3)
                    )
                )
            }
        }
        else -> {
            if (darkMode) {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1C1B1F),
                        Color(0xFF2D2C32),
                        Color(0xFF3E3D44)
                    )
                )
            } else {
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFBFE),
                        Color(0xFFF5F5F5),
                        Color(0xFFEEEEEE)
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
