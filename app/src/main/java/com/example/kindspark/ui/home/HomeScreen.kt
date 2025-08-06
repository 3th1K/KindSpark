package com.example.kindspark.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kindspark.ui.animations.LottieCelebration
import com.example.kindspark.ui.animations.MilestoneCelebration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showNotesDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kind Spark ✨",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            IconButton(onClick = { viewModel.refreshPrompt() }) {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = "Refresh"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Streak Card
        if (uiState.currentStreak > 0) {
            StreakCard(
                currentStreak = uiState.currentStreak,
                bestStreak = uiState.bestStreak
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Main Content
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                val errorMessage = uiState.error!!
                ErrorCard(
                    error = errorMessage,
                    onRetry = { viewModel.refreshPrompt() },
                    onDismiss = { viewModel.clearError() }
                )
            }

            uiState.dailyPrompt != null -> {
                val prompt = uiState.dailyPrompt!!
                DailyPromptCard(
                    promptWithCompletion = prompt,
                    isCompleted = uiState.isCompleted,
                    onMarkCompleted = { showNotesDialog = true },
                    onToggleFavorite = { viewModel.toggleFavorite() },
                    onSkipPrompt = { viewModel.skipCurrentPrompt() }
                )
            }
        }
    }

    // Notes Dialog
    if (showNotesDialog) {
        NotesDialog(
            onDismiss = { showNotesDialog = false },
            onConfirm = { notes ->
                viewModel.markAsCompleted(notes)
                showNotesDialog = false
            }
        )
    }

    // Celebration Animations
    if (uiState.celebrationMilestone != null) {
        val milestone = uiState.celebrationMilestone!!
        MilestoneCelebration(
            isVisible = uiState.showCelebration,
            milestone = milestone,
            onAnimationComplete = { viewModel.dismissCelebration() }
        )
    } else if (uiState.showCelebration) {
        LottieCelebration(
            isVisible = true,
            onAnimationComplete = { viewModel.dismissCelebration() },
            title = "Great Job! 🎉",
            subtitle = "You completed an act of kindness!",
            streakInfo = if (uiState.currentStreak > 1) "🔥 ${uiState.currentStreak} Day Streak!" else null
        )
    }
}

@Composable
private fun StreakCard(
    currentStreak: Int,
    bestStreak: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🔥 Current Streak",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "$currentStreak ${if (currentStreak == 1) "day" else "days"}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Best Streak: $bestStreak ${if (bestStreak == 1) "day" else "days"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun DailyPromptCard(
    promptWithCompletion: com.example.kindspark.data.KindnessPromptWithCompletion,
    isCompleted: Boolean,
    onMarkCompleted: () -> Unit,
    onToggleFavorite: () -> Unit,
    onSkipPrompt: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Today's Act of Kindness",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = promptWithCompletion.prompt.text,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = !isCompleted,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Button(
                        onClick = onMarkCompleted,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mark as Done")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onSkipPrompt,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Skip This One")
                    }
                }
            }

            AnimatedVisibility(
                visible = isCompleted,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✅ Completed!",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(onClick = onToggleFavorite) {
                            Icon(
                                if (promptWithCompletion.completion?.isFavorite == true)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = "Toggle Favorite",
                                tint = if (promptWithCompletion.completion?.isFavorite == true)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    if (!promptWithCompletion.completion?.notes.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Notes: ${promptWithCompletion.completion.notes}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorCard(
    error: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )

            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = onRetry) {
                    Text("Retry")
                }
                TextButton(onClick = onDismiss) {
                    Text("Dismiss")
                }
            }
        }
    }
}

@Composable
private fun NotesDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Add a note (optional)")
        },
        text = {
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("How did it go?") },
                maxLines = 3,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(notes) }) {
                Text("Complete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
