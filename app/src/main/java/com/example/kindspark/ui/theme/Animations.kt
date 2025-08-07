package com.example.kindspark.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

/**
 * Consistent animation system for KindSpark app
 */
object KindSparkAnimations {
    // Animation durations
    const val DURATION_SHORT = 200
    const val DURATION_MEDIUM = 300
    const val DURATION_LONG = 500

    // Easing curves for calming animations
    val EaseInOutQuart = CubicBezierEasing(0.76f, 0f, 0.24f, 1f)
    val EaseOutQuart = CubicBezierEasing(0.25f, 1f, 0.5f, 1f)
    val EaseInQuart = CubicBezierEasing(0.5f, 0f, 0.75f, 0f)

    // Tween specs for different use cases
    val gentleTween = tween<Float>(
        durationMillis = DURATION_MEDIUM,
        easing = EaseInOutQuart
    )

    val quickTween = tween<Float>(
        durationMillis = DURATION_SHORT,
        easing = EaseOutQuart
    )

    val slowTween = tween<Float>(
        durationMillis = DURATION_LONG,
        easing = EaseInOutQuart
    )

    // Specific animation specs for layout animations
    val gentleIntOffsetTween = tween<IntOffset>(
        durationMillis = DURATION_MEDIUM,
        easing = EaseInOutQuart
    )

    val quickIntOffsetTween = tween<IntOffset>(
        durationMillis = DURATION_SHORT,
        easing = EaseOutQuart
    )

    val gentleIntSizeTween = tween<IntSize>(
        durationMillis = DURATION_MEDIUM,
        easing = EaseInOutQuart
    )

    val quickIntSizeTween = tween<IntSize>(
        durationMillis = DURATION_SHORT,
        easing = EaseOutQuart
    )

    // Spring animations for natural feel
    val gentleSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    val responsiveSpring = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium
    )
}

/**
 * Crossfade wrapper for theme transitions
 */
@Composable
fun KindSparkCrossfade(
    targetState: Boolean,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = KindSparkAnimations.gentleTween,
    content: @Composable (targetState: Boolean) -> Unit
) {
    Crossfade(
        targetState = targetState,
        modifier = modifier,
        animationSpec = animationSpec,
        content = content
    )
}

/**
 * Enhanced AnimatedContent with slide transitions
 */
@Composable
fun <T> KindSparkAnimatedContent(
    targetState: T,
    modifier: Modifier = Modifier,
    transitionSpec: AnimatedContentTransitionScope<T>.() -> ContentTransform = {
        slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = KindSparkAnimations.gentleIntOffsetTween
        ) + fadeIn(animationSpec = KindSparkAnimations.gentleTween) togetherWith
        slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = KindSparkAnimations.gentleIntOffsetTween
        ) + fadeOut(animationSpec = KindSparkAnimations.gentleTween)
    },
    content: @Composable AnimatedContentScope.(targetState: T) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = transitionSpec,
        content = content
    )
}

/**
 * Gentle slide in/out animations
 */
fun slideInFromRight(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = KindSparkAnimations.gentleIntOffsetTween
    ) + fadeIn(animationSpec = KindSparkAnimations.gentleTween)
}

fun slideOutToLeft(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { -it },
        animationSpec = KindSparkAnimations.gentleIntOffsetTween
    ) + fadeOut(animationSpec = KindSparkAnimations.gentleTween)
}

fun slideInFromLeft(): EnterTransition {
    return slideInHorizontally(
        initialOffsetX = { -it },
        animationSpec = KindSparkAnimations.gentleIntOffsetTween
    ) + fadeIn(animationSpec = KindSparkAnimations.gentleTween)
}

fun slideOutToRight(): ExitTransition {
    return slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = KindSparkAnimations.gentleIntOffsetTween
    ) + fadeOut(animationSpec = KindSparkAnimations.gentleTween)
}

fun fadeInSlowly(): EnterTransition {
    return fadeIn(animationSpec = KindSparkAnimations.slowTween)
}

fun fadeOutQuickly(): ExitTransition {
    return fadeOut(animationSpec = KindSparkAnimations.quickTween)
}
