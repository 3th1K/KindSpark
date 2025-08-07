package com.example.kindspark.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kindspark.data.KindnessCompletion
import com.example.kindspark.data.KindnessPrompt
import com.example.kindspark.ui.history.HistoryFilter
import com.example.kindspark.ui.icons.SparklingHeart
import com.example.kindspark.ui.icons.CustomHistoryEdu
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun PromptHistoryCard(
    prompt: KindnessPrompt,
    completion: KindnessCompletion,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header with date and favorite button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Date
                Text(
                    text = formatCompletionDate(completion.completedDate),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )

                // Favorite button
                FavoriteButton(
                    isFavorite = completion.isFavorite,
                    onClick = onFavoriteToggle
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Prompt text
            Text(
                text = prompt.text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )

            // Category tag if available
            if (prompt.category.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                CategoryTag(category = prompt.category)
            }

            // Notes if available
            if (completion.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "\"${completion.notes}\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "favorite_scale"
    )

    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Outlined.SparklingHeart else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) {
                Color(0xFFE91E63) // Pink color for favorited items
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            modifier = Modifier.scale(scale)
        )
    }
}

@Composable
fun CategoryTag(
    category: String,
    modifier: Modifier = Modifier
) {
    val categoryColor = getCategoryColor(category)

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = categoryColor.copy(alpha = 0.1f)
    ) {
        Text(
            text = category.replaceFirstChar { it.uppercaseChar() },
            style = MaterialTheme.typography.labelSmall,
            color = categoryColor,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun HistoryFilterChips(
    currentFilter: HistoryFilter,
    onFilterChange: (HistoryFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HistoryFilter.values().forEach { filter ->
            FilterChip(
                selected = currentFilter == filter,
                onClick = { onFilterChange(filter) },
                label = {
                    Text(
                        text = when (filter) {
                            HistoryFilter.ALL -> "All"
                            HistoryFilter.COMPLETED -> "Completed"
                            HistoryFilter.FAVORITES -> "Favorites"
                        },
                        fontWeight = if (currentFilter == filter) FontWeight.SemiBold else FontWeight.Medium
                    )
                },
                leadingIcon = if (currentFilter == filter) {
                    {
                        Icon(
                            imageVector = when (filter) {
                                HistoryFilter.ALL -> Icons.Default.List
                                HistoryFilter.COMPLETED -> Icons.Default.CheckCircle
                                HistoryFilter.FAVORITES -> Icons.Default.Favorite
                            },
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}

@Composable
fun EmptyHistoryState(
    filter: HistoryFilter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Empty state icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = when (filter) {
                    HistoryFilter.ALL -> Icons.Filled.CustomHistoryEdu
                    HistoryFilter.COMPLETED -> Icons.Default.CheckCircle
                    HistoryFilter.FAVORITES -> Icons.Default.Favorite
                },
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = when (filter) {
                HistoryFilter.ALL -> "No prompts completed yet"
                HistoryFilter.COMPLETED -> "No completed prompts"
                HistoryFilter.FAVORITES -> "No favorite prompts yet"
            },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = when (filter) {
                HistoryFilter.ALL -> "Start completing daily kindness prompts to see them here"
                HistoryFilter.COMPLETED -> "Complete some kindness prompts to see them here"
                HistoryFilter.FAVORITES -> "Mark prompts as favorites by tapping the heart icon"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

// Helper functions
private fun formatCompletionDate(dateString: String): String {
    return try {
        val date = LocalDate.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
        date.format(formatter)
    } catch (e: Exception) {
        dateString
    }
}

private fun getCategoryColor(category: String): Color {
    return when (category.lowercase()) {
        "social" -> Color(0xFF2196F3) // Blue
        "family" -> Color(0xFF4CAF50) // Green
        "community" -> Color(0xFF9C27B0) // Purple
        "digital" -> Color(0xFF00BCD4) // Cyan
        "environment" -> Color(0xFF8BC34A) // Light Green
        "courtesy" -> Color(0xFFFF9800) // Orange
        "gratitude" -> Color(0xFFE91E63) // Pink
        "charity" -> Color(0xFF795548) // Brown
        "workplace" -> Color(0xFF607D8B) // Blue Grey
        "friendship" -> Color(0xFFFFEB3B) // Yellow
        "generosity" -> Color(0xFF3F51B5) // Indigo
        "empathy" -> Color(0xFF009688) // Teal
        "forgiveness" -> Color(0xFF673AB7) // Deep Purple
        "teaching" -> Color(0xFFFF5722) // Deep Orange
        "appreciation" -> Color(0xFFFFC107) // Amber
        else -> Color(0xFF757575) // Grey
    }
}
