package com.example.kindspark.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kindspark.data.preferences.UserPreferencesManager
import com.example.kindspark.ui.components.*
import com.example.kindspark.ui.icons.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showIntervalDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }

    // State for expandable sections
    var themeExpanded by rememberSaveable { mutableStateOf(true) }
    var notificationsExpanded by rememberSaveable { mutableStateOf(true) }
    var aboutExpanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Modern Header
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        )

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Theme Section
            ModernSettingSection(
                title = "Theme",
                isExpanded = themeExpanded,
                onToggleExpanded = { themeExpanded = !themeExpanded }
            ) {
                ModernSettingItem(
                    icon = Icons.Outlined.CustomPalette,
                    title = "App Theme",
                    subtitle = "Choose your preferred visual style"
                )

                // Dynamic Theme Previews
                LazyRow(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(UserPreferencesManager.AppTheme.values()) { theme ->
                        ThemePreviewCard(
                            theme = theme,
                            isSelected = theme == uiState.selectedTheme,
                            onClick = { viewModel.updateSelectedTheme(theme) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ModernSettingItem(
                    icon = Icons.Default.Settings,
                    title = "Dark Mode",
                    subtitle = "Enable dark theme across the app",
                    trailing = {
                        ModernSwitch(
                            checked = uiState.darkMode,
                            onCheckedChange = { viewModel.updateDarkMode(it) }
                        )
                    }
                )

                ModernSettingItem(
                    icon = Icons.Outlined.CustomLandscape,
                    title = "Calming Backgrounds",
                    subtitle = "Use gentle gradient backgrounds",
                    trailing = {
                        ModernSwitch(
                            checked = uiState.calmingBackground,
                            onCheckedChange = { viewModel.updateCalmingBackground(it) }
                        )
                    }
                )

                ModernSettingItem(
                    icon = Icons.Outlined.CustomAutoAwesome,
                    title = "Celebration Animations",
                    subtitle = "Show animations when completing tasks",
                    trailing = {
                        ModernSwitch(
                            checked = uiState.lottieAnimations,
                            onCheckedChange = { viewModel.updateLottieAnimations(it) }
                        )
                    }
                )
            }

            // Notifications Section
            ModernSettingSection(
                title = "Notifications",
                isExpanded = notificationsExpanded,
                onToggleExpanded = { notificationsExpanded = !notificationsExpanded }
            ) {
                ModernSettingItem(
                    icon = Icons.Outlined.AnimatedBell,
                    title = "Daily Reminders",
                    subtitle = "Get reminded to perform daily acts of kindness",
                    trailing = {
                        ModernSwitch(
                            checked = uiState.notificationEnabled,
                            onCheckedChange = { viewModel.updateNotificationEnabled(it) }
                        )
                    }
                )

                if (uiState.notificationEnabled) {
                    ModernSettingItem(
                        icon = Icons.Outlined.CustomSchedule,
                        title = "Notification Frequency",
                        subtitle = uiState.notificationInterval.displayName,
                        onClick = { showIntervalDialog = true }
                    )

                    ModernSettingItem(
                        icon = Icons.Outlined.CustomVolumeUp,
                        title = "Notification Sound",
                        subtitle = "Play sound with notifications",
                        trailing = {
                            ModernSwitch(
                                checked = uiState.notificationSound,
                                onCheckedChange = { viewModel.updateNotificationSound(it) }
                            )
                        }
                    )
                }
            }

            // About Section
            ModernSettingSection(
                title = "About",
                isExpanded = aboutExpanded,
                onToggleExpanded = { aboutExpanded = !aboutExpanded }
            ) {
                ModernSettingItem(
                    icon = Icons.Default.Info,
                    title = "Version",
                    subtitle = "1.0.0 - Just Getting Started!"
                )

                ModernSettingItem(
                    icon = Icons.Outlined.SparklingHeart,
                    title = "Made with ❤️",
                    subtitle = "Crafted to spread kindness and positivity"
                )

                ModernSettingItem(
                    icon = Icons.Outlined.AchievementStar,
                    title = "Rate the App",
                    subtitle = "Help us improve by sharing your feedback"
                )
            }

            // Reset Button at Bottom
            ResetSettingsButton(
                onClick = { showResetDialog = true }
            )

            // Add bottom padding for better scrolling experience
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Notification Interval Dialog
    if (showIntervalDialog) {
        AlertDialog(
            onDismissRequest = { showIntervalDialog = false },
            title = {
                Text(
                    text = "Notification Frequency",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Column {
                    Text(
                        text = "How often would you like to receive reminders?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    UserPreferencesManager.NotificationInterval.values().forEach { interval ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = interval == uiState.notificationInterval,
                                onClick = {
                                    viewModel.updateNotificationInterval(interval)
                                    showIntervalDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = interval.displayName,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showIntervalDialog = false }
                ) {
                    Text("Done")
                }
            }
        )
    }

    // Reset Settings Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(
                    text = "Reset Settings",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to reset all settings to their default values? This action cannot be undone.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetToDefaults()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showResetDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
