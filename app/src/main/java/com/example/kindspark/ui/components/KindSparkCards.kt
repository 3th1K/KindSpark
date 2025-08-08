package com.example.kindspark.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kindspark.ui.theme.*

/**
 * Reusable card components following KindSpark design system
 */

@Composable
fun KindSparkCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = KindSparkDimensions.CardElevation),
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable ColumnScope.() -> Unit
) {
    val cardModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Card(
        modifier = cardModifier.fillMaxWidth(),
        shape = RoundedCornerShape(KindSparkDimensions.CornerRadius),
        elevation = elevation,
        colors = colors
    ) {
        Column(
            modifier = Modifier.padding(KindSparkDimensions.OuterPadding),
            verticalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing),
            content = content
        )
    }
}

@Composable
fun KindSparkPromptCard(
    title: String,
    prompt: String,
    isCompleted: Boolean,
    onMarkCompleted: () -> Unit,
    onSkipPrompt: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }

    KindSparkAnimatedContent(
        targetState = isCompleted,
        modifier = modifier.padding(horizontal = KindSparkDimensions.OuterPadding)
    ) { completed ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { if (!completed) isExpanded = !isExpanded },
            shape = RoundedCornerShape(KindSparkDimensions.CornerRadiusLarge),
            elevation = CardDefaults.cardElevation(defaultElevation = KindSparkDimensions.CardElevation),
            colors = CardDefaults.cardColors(
                containerColor = if (completed) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (!completed) {
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                                    MaterialTheme.colorScheme.surface,
                                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.05f)
                                ),
                                radius = 800f
                            )
                        } else {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
                                )
                            )
                        }
                    )
                    .padding(KindSparkDimensions.OuterPadding)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = if (completed) {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )

                        if (completed) {
                            KindSparkCheckIcon(
                                color = MaterialTheme.colorScheme.primary,
                                size = KindSparkDimensions.IconLarge
                            )
                        } else {
                            KindSparkHeartIcon(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                size = KindSparkDimensions.IconLarge
                            )
                        }
                    }

                    // Prompt text
                    AnimatedVisibility(
                        visible = isExpanded || completed,
                        enter = fadeInSlowly() + expandVertically(
                            animationSpec = KindSparkAnimations.gentleIntSizeTween
                        ),
                        exit = fadeOutQuickly() + shrinkVertically(
                            animationSpec = KindSparkAnimations.quickIntSizeTween
                        )
                    ) {
                        Text(
                            text = prompt,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (completed) {
                                MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            },
                            textAlign = TextAlign.Start
                        )
                    }

                    // Action buttons
                    if (!completed && isExpanded) {
                        AnimatedVisibility(
                            visible = true,
                            enter = slideInFromRight(),
                            exit = slideOutToLeft()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing)
                            ) {
                                KindSparkButton(
                                    text = "Complete",
                                    onClick = onMarkCompleted,
                                    modifier = Modifier.weight(1f),
                                    style = KindSparkButtonStyle.Primary
                                )

                                KindSparkButton(
                                    text = "Skip",
                                    onClick = onSkipPrompt,
                                    modifier = Modifier.weight(1f),
                                    style = KindSparkButtonStyle.Secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KindSparkStreakCard(
    currentStreak: Int,
    bestStreak: Int,
    modifier: Modifier = Modifier
) {
    KindSparkCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Current Streak",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing)
                ) {
                    Text(
                        text = "$currentStreak",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )

                    KindSparkStreakIcon(
                        color = MaterialTheme.colorScheme.secondary,
                        size = KindSparkDimensions.IconLarge
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Best: $bestStreak",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun KindSparkStatsCard(
    title: String,
    value: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer
) {
    KindSparkCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing)
        ) {
            icon()

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = contentColor,
                textAlign = TextAlign.Center
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = contentColor.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}
