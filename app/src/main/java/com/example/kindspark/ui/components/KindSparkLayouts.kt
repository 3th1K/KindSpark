package com.example.kindspark.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
 * Layout and screen components following KindSpark design system
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KindSparkScaffold(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    snackbarHost: (@Composable () -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            KindSparkTopAppBar(
                title = title,
                navigationIcon = navigationIcon,
                actions = actions
            )
        },
        floatingActionButton = floatingActionButton ?: {},
        bottomBar = bottomBar ?: {},
        snackbarHost = snackbarHost ?: {},
        containerColor = containerColor,
        contentColor = contentColor,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KindSparkTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon ?: {},
        actions = actions ?: {},
        colors = colors
    )
}

@Composable
fun KindSparkContentContainer(
    modifier: Modifier = Modifier,
    scrollable: Boolean = true,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(KindSparkDimensions.OuterPadding),
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .then(
                if (scrollable) {
                    Modifier.verticalScroll(scrollState)
                } else {
                    Modifier
                }
            )
            .padding(KindSparkDimensions.OuterPadding),
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = verticalArrangement,
        content = content
    )
}

@Composable
fun KindSparkSection(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    action: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing)
    ) {
        // Section header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(KindSparkDimensions.SpacingXS)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                }
            }

            action?.invoke()
        }

        // Section content
        Column(
            verticalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing),
            content = content
        )
    }
}

@Composable
fun KindSparkGradientBackground(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
        MaterialTheme.colorScheme.background
    ),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = colors)
            ),
        content = content
    )
}

@Composable
fun KindSparkBottomNavigation(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    items: List<KindSparkBottomNavItem>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemSelected(index) },
                icon = {
                    KindSparkAnimatedContent(
                        targetState = selectedItem == index
                    ) { isSelected ->
                        if (isSelected) {
                            item.selectedIcon()
                        } else {
                            item.unselectedIcon()
                        }
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

data class KindSparkBottomNavItem(
    val label: String,
    val selectedIcon: @Composable () -> Unit,
    val unselectedIcon: @Composable () -> Unit
)

@Composable
fun KindSparkLoadingIndicator(
    modifier: Modifier = Modifier,
    message: String = "Loading...",
    color: Color = MaterialTheme.colorScheme.primary
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing)
    ) {
        CircularProgressIndicator(
            color = color,
            strokeWidth = 3.dp
        )

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun KindSparkEmptyState(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(KindSparkDimensions.OuterPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(KindSparkDimensions.InnerSpacing)
    ) {
        icon?.invoke()

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )

        action?.invoke()
    }
}
