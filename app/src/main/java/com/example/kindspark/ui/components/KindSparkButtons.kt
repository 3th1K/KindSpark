package com.example.kindspark.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kindspark.ui.theme.*

/**
 * Consistent button components for KindSpark app
 */

enum class KindSparkButtonStyle {
    Primary,
    Secondary,
    Tertiary,
    Outlined
}

@Composable
fun KindSparkButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: KindSparkButtonStyle = KindSparkButtonStyle.Primary,
    enabled: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
    isLoading: Boolean = false
) {
    val animatedScale by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.95f,
        animationSpec = KindSparkAnimations.gentleSpring,
        label = "button_scale"
    )

    when (style) {
        KindSparkButtonStyle.Primary -> {
            Button(
                onClick = onClick,
                modifier = modifier
                    .height(KindSparkDimensions.ButtonHeight)
                    .fillMaxWidth(),
                enabled = enabled && !isLoading,
                shape = RoundedCornerShape(KindSparkDimensions.CornerRadius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                contentPadding = PaddingValues(
                    horizontal = KindSparkDimensions.OuterPadding,
                    vertical = KindSparkDimensions.InnerSpacing
                )
            ) {
                KindSparkButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        KindSparkButtonStyle.Secondary -> {
            Button(
                onClick = onClick,
                modifier = modifier
                    .height(KindSparkDimensions.ButtonHeight)
                    .fillMaxWidth(),
                enabled = enabled && !isLoading,
                shape = RoundedCornerShape(KindSparkDimensions.CornerRadius),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                contentPadding = PaddingValues(
                    horizontal = KindSparkDimensions.OuterPadding,
                    vertical = KindSparkDimensions.InnerSpacing
                )
            ) {
                KindSparkButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            }
        }

        KindSparkButtonStyle.Tertiary -> {
            TextButton(
                onClick = onClick,
                modifier = modifier
                    .height(KindSparkDimensions.ButtonHeight)
                    .fillMaxWidth(),
                enabled = enabled && !isLoading,
                shape = RoundedCornerShape(KindSparkDimensions.CornerRadius),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(
                    horizontal = KindSparkDimensions.OuterPadding,
                    vertical = KindSparkDimensions.InnerSpacing
                )
            ) {
                KindSparkButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }

        KindSparkButtonStyle.Outlined -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier
                    .height(KindSparkDimensions.ButtonHeight)
                    .fillMaxWidth(),
                enabled = enabled && !isLoading,
                shape = RoundedCornerShape(KindSparkDimensions.CornerRadius),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.5.dp
                ),
                contentPadding = PaddingValues(
                    horizontal = KindSparkDimensions.OuterPadding,
                    vertical = KindSparkDimensions.InnerSpacing
                )
            ) {
                KindSparkButtonContent(
                    text = text,
                    icon = icon,
                    isLoading = isLoading,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun KindSparkButtonContent(
    text: String,
    icon: (@Composable () -> Unit)?,
    isLoading: Boolean,
    contentColor: Color
) {
    KindSparkCrossfade(
        targetState = isLoading
    ) { loading ->
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(KindSparkDimensions.IconMedium),
                color = contentColor,
                strokeWidth = 2.dp
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.invoke()

                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    color = contentColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun KindSparkFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {
        KindSparkPlusIcon(
            color = MaterialTheme.colorScheme.onPrimary,
            size = KindSparkDimensions.IconMedium
        )
    },
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(KindSparkDimensions.CornerRadius),
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = KindSparkDimensions.CardElevation
        )
    ) {
        icon()
    }
}

@Composable
fun KindSparkIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        icon()
    }
}
