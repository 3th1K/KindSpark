package com.example.kindspark.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kindspark.data.preferences.UserPreferencesManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showIntervalDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Settings List
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    // Notifications Section
                    SettingSectionHeader(title = "Notifications")

                    SettingItem(
                        icon = Icons.Default.Notifications,
                        title = "Daily Reminders",
                        subtitle = "Get reminded to perform daily acts of kindness",
                        trailing = {
                            Switch(
                                checked = uiState.notificationEnabled,
                                onCheckedChange = { viewModel.updateNotificationEnabled(it) }
                            )
                        }
                    )

                    if (uiState.notificationEnabled) {
                        SettingItem(
                            icon = Icons.Default.DateRange,
                            title = "Notification Frequency",
                            subtitle = uiState.notificationInterval.displayName,
                            onClick = { showIntervalDialog = true }
                        )

                        SettingItem(
                            icon = Icons.Default.Notifications,
                            title = "Notification Sound",
                            subtitle = "Play sound with notifications",
                            trailing = {
                                Switch(
                                    checked = uiState.notificationSound,
                                    onCheckedChange = { viewModel.updateNotificationSound(it) }
                                )
                            }
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                    // Appearance Section
                    SettingSectionHeader(title = "Appearance")

                    SettingItem(
                        icon = Icons.Default.Settings,
                        title = "App Theme",
                        subtitle = uiState.selectedTheme.displayName,
                        onClick = { showThemeDialog = true }
                    )

                    SettingItem(
                        icon = Icons.Default.Star,
                        title = "Calming Backgrounds",
                        subtitle = "Use gentle gradient backgrounds",
                        trailing = {
                            Switch(
                                checked = uiState.calmingBackground,
                                onCheckedChange = { viewModel.updateCalmingBackground(it) }
                            )
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                    // Experience Section
                    SettingSectionHeader(title = "Experience")

                    SettingItem(
                        icon = Icons.Default.Star,
                        title = "Celebration Animations",
                        subtitle = "Show animations when completing tasks",
                        trailing = {
                            Switch(
                                checked = uiState.lottieAnimations,
                                onCheckedChange = { viewModel.updateLottieAnimations(it) }
                            )
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                    // About Section
                    SettingSectionHeader(title = "About")

                    SettingItem(
                        icon = Icons.Default.Info,
                        title = "Version",
                        subtitle = "2.0.0 - Enhanced with Phase 1 & 2 Features"
                    )

                    SettingItem(
                        icon = Icons.Default.Favorite,
                        title = "Made with ❤️",
                        subtitle = "Spreading kindness one act at a time"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Notification Interval Dialog
    if (showIntervalDialog) {
        NotificationIntervalDialog(
            currentInterval = uiState.notificationInterval,
            onIntervalSelected = { interval ->
                viewModel.updateNotificationInterval(interval)
                showIntervalDialog = false
            },
            onDismiss = { showIntervalDialog = false }
        )
    }

    // Theme Selection Dialog
    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = uiState.selectedTheme,
            onThemeSelected = { theme ->
                viewModel.updateSelectedTheme(theme)
                showThemeDialog = false
            },
            onDismiss = { showThemeDialog = false }
        )
    }
}

@Composable
private fun SettingSectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(16.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val itemModifier = if (onClick != null) {
        modifier.then(Modifier.clickable { onClick() })
    } else {
        modifier
    }

    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        leadingContent = {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingContent = trailing,
        modifier = itemModifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationIntervalDialog(
    currentInterval: UserPreferencesManager.NotificationInterval,
    onIntervalSelected: (UserPreferencesManager.NotificationInterval) -> Unit,
    onDismiss: () -> Unit
) {
    val intervals = UserPreferencesManager.NotificationInterval.values()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Select Notification Frequency")
        },
        text = {
            Column {
                intervals.forEach { interval ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onIntervalSelected(interval) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = interval == currentInterval,
                            onClick = { onIntervalSelected(interval) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(interval.displayName)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeSelectionDialog(
    currentTheme: UserPreferencesManager.AppTheme,
    onThemeSelected: (UserPreferencesManager.AppTheme) -> Unit,
    onDismiss: () -> Unit
) {
    val themes = UserPreferencesManager.AppTheme.values()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Select App Theme")
        },
        text = {
            Column {
                themes.forEach { theme ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onThemeSelected(theme) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = theme == currentTheme,
                            onClick = { onThemeSelected(theme) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(theme.displayName)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
