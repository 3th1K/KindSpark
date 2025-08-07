package com.example.kindspark.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kindspark.data.preferences.UserPreferencesManager
import com.example.kindspark.ui.theme.*;

/**
 * Themed UI components that adapt to the new visual themes
 * with improved contrast and legibility for dark mode
 */

@Composable
fun ThemedCard(
    modifier: Modifier = Modifier,
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardColors = getThemedCardColors(theme, isDarkMode)
    val elevation = if (isDarkMode) 8.dp else 4.dp

    Card(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else Modifier
            ),
        colors = cardColors,
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun ThemedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean,
    enabled: Boolean = true,
    text: String
) {
    val buttonColors = getThemedButtonColors(theme, isDarkMode)

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = buttonColors,
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isDarkMode) 6.dp else 3.dp
        )
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            color = if (isDarkMode) Color.White else Color.White
        )
    }
}

@Composable
fun ThemedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean,
    icon: @Composable () -> Unit,
    contentDescription: String? = null
) {
    val iconButtonColors = getThemedIconButtonColors(theme, isDarkMode)

    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .background(
                color = iconButtonColors.containerColor,
                shape = CircleShape
            )
            .border(
                width = if (isDarkMode) 1.dp else 0.dp,
                color = iconButtonColors.contentColor.copy(alpha = 0.2f),
                shape = CircleShape
            ),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = iconButtonColors.contentColor
        )
    ) {
        icon()
    }
}

@Composable
fun ThemedSurface(
    modifier: Modifier = Modifier,
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(8.dp),
    content: @Composable () -> Unit
) {
    val surfaceColors = getThemedSurfaceColors(theme, isDarkMode)

    Surface(
        modifier = modifier,
        color = surfaceColors.containerColor,
        contentColor = surfaceColors.contentColor,
        shape = shape,
        shadowElevation = if (isDarkMode) 4.dp else 2.dp
    ) {
        content()
    }
}

@Composable
fun ThemedText(
    text: String,
    modifier: Modifier = Modifier,
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign? = null,
    fontWeight: FontWeight? = null
) {
    val textColor = getThemedTextColor(theme, isDarkMode)

    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        style = style.copy(
            fontWeight = fontWeight ?: style.fontWeight,
            fontSize = if (isDarkMode) style.fontSize * 1.05f else style.fontSize
        ),
        textAlign = textAlign
    )
}

// Helper functions to get themed colors
@Composable
private fun getThemedCardColors(
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean
): CardColors {
    return when (theme) {
        UserPreferencesManager.AppTheme.CALM_OCEAN -> {
            if (isDarkMode) {
                CardDefaults.cardColors(
                    containerColor = Color(0xFF1B4A4F),
                    contentColor = CalmOceanContainer
                )
            } else {
                CardDefaults.cardColors(
                    containerColor = CalmOceanSurface,
                    contentColor = CalmOceanPrimaryDark
                )
            }
        }
        UserPreferencesManager.AppTheme.WARM_SUNSET -> {
            if (isDarkMode) {
                CardDefaults.cardColors(
                    containerColor = Color(0xFF4A2C2A),
                    contentColor = WarmSunsetContainer
                )
            } else {
                CardDefaults.cardColors(
                    containerColor = WarmSunsetSurface,
                    contentColor = WarmSunsetPrimaryDark
                )
            }
        }
        UserPreferencesManager.AppTheme.SERENE_FOREST -> {
            if (isDarkMode) {
                CardDefaults.cardColors(
                    containerColor = Color(0xFF2E4A2F),
                    contentColor = SereneForestContainer
                )
            } else {
                CardDefaults.cardColors(
                    containerColor = SereneForestSurface,
                    contentColor = SereneForestPrimaryDark
                )
            }
        }
        UserPreferencesManager.AppTheme.MIDNIGHT_CALM -> {
            if (isDarkMode) {
                CardDefaults.cardColors(
                    containerColor = MidnightCalmSurface,
                    contentColor = Color(0xFFCFD8DC)
                )
            } else {
                CardDefaults.cardColors(
                    containerColor = Color(0xFFE8EAF6),
                    contentColor = MidnightCalmPrimary
                )
            }
        }
        else -> CardDefaults.cardColors()
    }
}

@Composable
private fun getThemedButtonColors(
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean
): ButtonColors {
    return when (theme) {
        UserPreferencesManager.AppTheme.CALM_OCEAN -> {
            ButtonDefaults.buttonColors(
                containerColor = if (isDarkMode) CalmOceanPrimaryDark else CalmOceanPrimary,
                contentColor = CalmOceanAccent
            )
        }
        UserPreferencesManager.AppTheme.WARM_SUNSET -> {
            ButtonDefaults.buttonColors(
                containerColor = if (isDarkMode) WarmSunsetPrimaryDark else WarmSunsetPrimary,
                contentColor = Color.White
            )
        }
        UserPreferencesManager.AppTheme.SERENE_FOREST -> {
            ButtonDefaults.buttonColors(
                containerColor = if (isDarkMode) SereneForestPrimary else SereneForestPrimaryDark,
                contentColor = Color.White
            )
        }
        UserPreferencesManager.AppTheme.MIDNIGHT_CALM -> {
            ButtonDefaults.buttonColors(
                containerColor = if (isDarkMode) MidnightCalmAccent else MidnightCalmPrimary,
                contentColor = Color.White
            )
        }
        else -> ButtonDefaults.buttonColors()
    }
}

private data class IconButtonColors(
    val containerColor: Color,
    val contentColor: Color
)

private data class SurfaceColors(
    val containerColor: Color,
    val contentColor: Color
)

@Composable
private fun getThemedIconButtonColors(
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean
): IconButtonColors {
    return when (theme) {
        UserPreferencesManager.AppTheme.CALM_OCEAN -> {
            if (isDarkMode) {
                IconButtonColors(
                    containerColor = Color(0xFF004D40).copy(alpha = 0.8f),
                    contentColor = Color(0xFFB2EBF2)
                )
            } else {
                IconButtonColors(
                    containerColor = Color(0xFFE0F2F1),
                    contentColor = Color(0xFF00695C)
                )
            }
        }
        UserPreferencesManager.AppTheme.WARM_SUNSET -> {
            if (isDarkMode) {
                IconButtonColors(
                    containerColor = Color(0xFFBF360C).copy(alpha = 0.8f),
                    contentColor = Color(0xFFFFCCBC)
                )
            } else {
                IconButtonColors(
                    containerColor = Color(0xFFFFF3E0),
                    contentColor = Color(0xFFE65100)
                )
            }
        }
        UserPreferencesManager.AppTheme.SERENE_FOREST -> {
            if (isDarkMode) {
                IconButtonColors(
                    containerColor = Color(0xFF2E7D32).copy(alpha = 0.8f),
                    contentColor = Color(0xFFC8E6C9)
                )
            } else {
                IconButtonColors(
                    containerColor = Color(0xFFE8F5E8),
                    contentColor = Color(0xFF2E7D32)
                )
            }
        }
        UserPreferencesManager.AppTheme.MIDNIGHT_CALM -> {
            if (isDarkMode) {
                IconButtonColors(
                    containerColor = Color(0xFF283593).copy(alpha = 0.8f),
                    contentColor = Color(0xFFD1D1E0)
                )
            } else {
                IconButtonColors(
                    containerColor = Color(0xFFE8EAF6),
                    contentColor = Color(0xFF1A237E)
                )
            }
        }
        else -> IconButtonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
private fun getThemedSurfaceColors(
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean
): SurfaceColors {
    return when (theme) {
        UserPreferencesManager.AppTheme.CALM_OCEAN -> {
            if (isDarkMode) {
                SurfaceColors(
                    containerColor = Color(0xFF00332B),
                    contentColor = Color(0xFFB2EBF2)
                )
            } else {
                SurfaceColors(
                    containerColor = Color(0xFFE0F2F1),
                    contentColor = Color(0xFF004D40)
                )
            }
        }
        UserPreferencesManager.AppTheme.WARM_SUNSET -> {
            if (isDarkMode) {
                SurfaceColors(
                    containerColor = Color(0xFF5D4037),
                    contentColor = Color(0xFFFFCCBC)
                )
            } else {
                SurfaceColors(
                    containerColor = Color(0xFFFFF3E0),
                    contentColor = Color(0xFF3E2723)
                )
            }
        }
        UserPreferencesManager.AppTheme.SERENE_FOREST -> {
            if (isDarkMode) {
                SurfaceColors(
                    containerColor = Color(0xFF2E7D32),
                    contentColor = Color(0xFFC8E6C9)
                )
            } else {
                SurfaceColors(
                    containerColor = Color(0xFFE8F5E8),
                    contentColor = Color(0xFF1B5E20)
                )
            }
        }
        UserPreferencesManager.AppTheme.MIDNIGHT_CALM -> {
            if (isDarkMode) {
                SurfaceColors(
                    containerColor = Color(0xFF2E2E3A),
                    contentColor = Color(0xFFD1D1E0)
                )
            } else {
                SurfaceColors(
                    containerColor = Color(0xFFE8EAF6),
                    contentColor = Color(0xFF1A1A2E)
                )
            }
        }
        else -> SurfaceColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun getThemedTextColor(
    theme: UserPreferencesManager.AppTheme,
    isDarkMode: Boolean
): Color {
    return when (theme) {
        UserPreferencesManager.AppTheme.CALM_OCEAN -> {
            if (isDarkMode) Color(0xFFE0F7FA) else Color(0xFF004D40)
        }
        UserPreferencesManager.AppTheme.WARM_SUNSET -> {
            if (isDarkMode) Color(0xFFFFE0B2) else Color(0xFF3E2723)
        }
        UserPreferencesManager.AppTheme.SERENE_FOREST -> {
            if (isDarkMode) Color(0xFFE8F5E8) else Color(0xFF1B5E20)
        }
        UserPreferencesManager.AppTheme.MIDNIGHT_CALM -> {
            if (isDarkMode) Color(0xFFD1D1E0) else Color(0xFF1A1A2E)
        }
        else -> MaterialTheme.colorScheme.onSurface
    }
}
