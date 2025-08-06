package com.example.kindspark.ui.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun LottieCelebration(
    isVisible: Boolean,
    onAnimationComplete: () -> Unit,
    title: String = "Great Job! 🎉",
    subtitle: String = "You completed an act of kindness!",
    streakInfo: String? = null,
    modifier: Modifier = Modifier
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = onAnimationComplete,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Card(
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(32.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Animated emoji as placeholder for Lottie
                            // TODO: Replace with actual Lottie animation when library is added
                            AnimatedEmoji()
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Text(
                                text = title,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.Center
                            )
                            
                            streakInfo?.let { info ->
                                Spacer(modifier = Modifier.height(16.dp))
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(
                                        text = info,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.padding(16.dp),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Button(
                                onClick = onAnimationComplete,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Continue")
                            }
                        }
                    }
                }
            }
        }
        
        LaunchedEffect(isVisible) {
            if (isVisible) {
                // Auto-dismiss after 3 seconds if user doesn't interact
                delay(3000)
                onAnimationComplete()
            }
        }
    }
}

@Composable
private fun AnimatedEmoji() {
    // Placeholder animated emoji - can be replaced with Lottie when added
    var scale by remember { mutableFloatStateOf(1f) }
    
    LaunchedEffect(Unit) {
        while (true) {
            scale = 1.2f
            delay(500)
            scale = 1f
            delay(500)
        }
    }
    
    Text(
        text = "🎉✨🌟",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier
            .padding(16.dp)
    )
}

// Milestone-specific celebrations
@Composable
fun MilestoneCelebration(
    isVisible: Boolean,
    milestone: Int,
    onAnimationComplete: () -> Unit
) {
    val (title, subtitle, emoji) = when (milestone) {
        3 -> Triple("Amazing Start! 🌱", "3-day streak achieved!", "🌱🌿🍃")
        7 -> Triple("One Week Strong! 🔥", "7-day kindness streak!", "🔥💪⭐")
        14 -> Triple("Two Weeks of Kindness! 🏆", "You're on fire!", "🏆🎯🌟")
        21 -> Triple("Habit Formed! 🎪", "21 days of kindness!", "🎪🎊🎈")
        30 -> Triple("Monthly Champion! 👑", "30 days of spreading joy!", "👑💎✨")
        50 -> Triple("Kindness Hero! 🦸", "50 days of making a difference!", "🦸🌈⚡")
        100 -> Triple("Legendary! 🏅", "100 days of pure kindness!", "🏅🎆🌠")
        else -> Triple("Streak Milestone! 🎉", "$milestone days of kindness!", "🎉🌟✨")
    }

    LottieCelebration(
        isVisible = isVisible,
        onAnimationComplete = onAnimationComplete,
        title = title,
        subtitle = subtitle,
        streakInfo = "🔥 $milestone Day Streak!"
    )
}
