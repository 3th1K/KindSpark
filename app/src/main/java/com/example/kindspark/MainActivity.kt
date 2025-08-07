package com.example.kindspark

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kindspark.data.*
import com.example.kindspark.data.preferences.UserPreferencesManager
import com.example.kindspark.notifications.DailyNotificationScheduler
import com.example.kindspark.notifications.NotificationScheduler
import com.example.kindspark.ui.home.*
import com.example.kindspark.ui.settings.*
import com.example.kindspark.ui.history.*
import com.example.kindspark.ui.icons.HistoryNav
import com.example.kindspark.ui.icons.CustomList
import com.example.kindspark.ui.theme.KindSparkAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Handle the permission result if needed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            KindSparkApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KindSparkApp() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Initialize dependencies
    val database = remember { KindnessDatabase.getDatabase(context) }
    val repository = remember { KindnessRepository(database.kindnessDao()) }
    val userPreferencesManager = remember { UserPreferencesManager(context) }
    val notificationScheduler = remember { NotificationScheduler(context, userPreferencesManager) }

    // Initialize database with default prompts
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            repository.initializeDatabase()
        }
    }

    // Create navigation controller outside of theme scope to prevent recomposition issues
    val navController = rememberNavController()

    // Collect theme preferences from UserPreferencesManager
    val selectedTheme by userPreferencesManager.selectedTheme.collectAsStateWithLifecycle(initialValue = UserPreferencesManager.AppTheme.LIGHT)
    val calmingBackground by userPreferencesManager.calmingBackground.collectAsStateWithLifecycle(initialValue = true)
    val darkMode by userPreferencesManager.darkMode.collectAsStateWithLifecycle(initialValue = false)

    KindSparkAppTheme(
        selectedTheme = selectedTheme,
        calmingBackground = calmingBackground,
        darkTheme = darkMode
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.label) },
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    val viewModel: HomeViewModel = viewModel(
                        factory = HomeViewModelFactory(
                            repository,
                            userPreferencesManager,
                            notificationScheduler
                        )
                    )
                    HomeScreen(viewModel = viewModel)
                }

                composable("history") {
                    val viewModel: PromptHistoryViewModel = viewModel(
                        factory = PromptHistoryViewModelFactory(repository)
                    )
                    PromptHistoryScreen(viewModel = viewModel)
                }

                composable("settings") {
                    val viewModel: SettingsViewModel = viewModel(
                        factory = SettingsViewModelFactory(
                            userPreferencesManager,
                            notificationScheduler
                        )
                    )
                    SettingsScreen(viewModel = viewModel)
                }
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("home", "Home", Icons.Default.Home),
    BottomNavItem("history", "History", Icons.Filled.HistoryNav),
    BottomNavItem("settings", "Settings", Icons.Default.Settings)
)
