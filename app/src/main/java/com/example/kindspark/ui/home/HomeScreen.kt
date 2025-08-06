package com.example.kindspark.ui.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kindspark.ui.animations.LottieCelebration
import com.example.kindspark.ui.animations.MilestoneCelebration
import com.example.kindspark.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showNotesDialog by remember { mutableStateOf(false) }
    var showCelebrationBadge by remember { mutableStateOf(false) }

    // Trigger celebration badge when completed
    LaunchedEffect(uiState.isCompleted) {
        if (uiState.isCompleted && uiState.currentStreak > 0) {
            showCelebrationBadge = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        ) {
            // Modern Header Section
            ModernHeader(
                onRefresh = { viewModel.refreshPrompt() },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Streak Card
            if (uiState.currentStreak > 0) {
                ModernStreakCard(
                    currentStreak = uiState.currentStreak,
                    bestStreak = uiState.bestStreak
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Main Content
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading your daily kindness...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                uiState.error != null -> {
                    ModernErrorCard(
                        error = uiState.error!!,
                        onRetry = { viewModel.refreshPrompt() },
                        onDismiss = { viewModel.clearError() }
                    )
                }

                uiState.dailyPrompt != null -> {
                    val prompt = uiState.dailyPrompt!!
                    ModernPromptCard(
                        title = "Today's Kindness Prompt",
                        prompt = prompt.prompt.text,
                        isCompleted = uiState.isCompleted,
                        onMarkCompleted = { showNotesDialog = true },
                        onSkipPrompt = { viewModel.skipCurrentPrompt() }
                    )

                    // Completed state with favorite option
                    if (uiState.isCompleted) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CompletedSection(
                            promptWithCompletion = prompt,
                            onToggleFavorite = { viewModel.toggleFavorite() }
                        )
                    }
                }
            }
        }

        // Celebration Badge
        CelebrationBadge(
            isVisible = showCelebrationBadge && uiState.currentStreak >= 5,
            streakCount = uiState.currentStreak,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }

    // Notes Dialog
    if (showNotesDialog) {
        NotesDialog(
            onDismiss = { showNotesDialog = false },
            onConfirm = { notes ->
                viewModel.markAsCompleted(notes)
                showNotesDialog = false
                showCelebrationBadge = true
            }
        )
    }

    // Celebration Animations
    if (uiState.celebrationMilestone != null) {
        val milestone = uiState.celebrationMilestone!!
        MilestoneCelebration(
            isVisible = uiState.showCelebration,
            milestone = milestone,
            onAnimationComplete = {
                viewModel.dismissCelebration()
                showCelebrationBadge = false
            }
        )
    } else if (uiState.showCelebration) {
        LottieCelebration(
            isVisible = true,
            onAnimationComplete = {
                viewModel.dismissCelebration()
                showCelebrationBadge = false
            },
            title = "Great Job! 🎉",
            subtitle = "You completed an act of kindness!",
            streakInfo = if (uiState.currentStreak > 1) "🔥 ${uiState.currentStreak} Day Streak!" else null
        )
    }
}

@Composable
private fun ModernHeader(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Kind Spark",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Spreading kindness, one act at a time ✨",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        FilledTonalIconButton(
            onClick = onRefresh,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = "Refresh",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CompletedSection(
    promptWithCompletion: com.example.kindspark.data.KindnessPromptWithCompletion,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "✅ Completed!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(12.dp))

                FilledTonalIconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        if (promptWithCompletion.completion?.isFavorite == true)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle Favorite",
                        tint = if (promptWithCompletion.completion?.isFavorite == true)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (!promptWithCompletion.completion?.notes.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "\"${promptWithCompletion.completion.notes}\"",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ModernErrorCard(
    error: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "😔",
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Something went wrong",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Dismiss")
                }

                FilledTonalButton(
                    onClick = onRetry,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Try Again")
                }
            }
        }
    }
}

// Keep existing NotesDialog component unchanged
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
