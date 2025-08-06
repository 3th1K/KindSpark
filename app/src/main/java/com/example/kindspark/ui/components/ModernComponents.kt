package com.example.kindspark.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ModernPromptCard(
    title: String,
    prompt: String,
    isCompleted: Boolean,
    onMarkCompleted: () -> Unit,
    onSkipPrompt: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.05f)
                        ),
                        radius = 800f
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Animated Lottie Icon
                LottieAnimatedIcon()

                Spacer(modifier = Modifier.height(24.dp))

                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Quote-styled prompt
                Text(
                    text = "\"$prompt\"",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontStyle = FontStyle.Italic,
                        fontSize = 22.sp,
                        lineHeight = 32.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Action Buttons
                AnimatedVisibility(
                    visible = !isCompleted,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        GradientButton(
                            text = "Mark Done",
                            onClick = onMarkCompleted,
                            isPrimary = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        GradientButton(
                            text = "Skip",
                            onClick = onSkipPrompt,
                            isPrimary = false,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LottieAnimatedIcon(
    modifier: Modifier = Modifier
) {
    // Using a fallback animated emoji since we don't have Lottie files yet
    var scale by remember { mutableStateOf(1f) }

    LaunchedEffect(Unit) {
        while (true) {
            animate(
                initialValue = 1f,
                targetValue = 1.2f,
                animationSpec = tween(1000, easing = FastOutSlowInEasing)
            ) { value, _ -> scale = value }

            animate(
                initialValue = 1.2f,
                targetValue = 1f,
                animationSpec = tween(1000, easing = FastOutSlowInEasing)
            ) { value, _ -> scale = value }
        }
    }

    Text(
        text = "💖",
        fontSize = (48 * scale).sp,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    isPrimary: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = if (isPrimary) {
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primaryContainer
        )
    } else {
        listOf(
            MaterialTheme.colorScheme.secondaryContainer,
            MaterialTheme.colorScheme.secondary
        )
    }

    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.horizontalGradient(colors)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(28.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp,
            pressedElevation = 2.dp
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = if (isPrimary)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun CelebrationBadge(
    isVisible: Boolean,
    streakCount: Int,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        ) + fadeIn(),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(300)
        ) + fadeOut(),
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🎉",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$streakCount-day streak!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}

@Composable
fun ModernStreakCard(
    currentStreak: Int,
    bestStreak: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StreakItem(
                    emoji = "🔥",
                    label = "Current",
                    value = currentStreak,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(40.dp)
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
                        )
                )

                StreakItem(
                    emoji = "🏆",
                    label = "Best",
                    value = bestStreak,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StreakItem(
    emoji: String,
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
        )
        Text(
            text = "$value",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
