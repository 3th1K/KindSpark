package com.example.kindspark.ui.icons

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlin.math.sin

public val Icons.Outlined.AnimatedBell: ImageVector
    get() {
        if (_animatedBell != null) {
            return _animatedBell!!
        }
        _animatedBell = materialIcon(name = "AnimatedBell") {
            materialPath {
                // Bell body
                moveTo(12.0f, 22.0f)
                curveTo(13.1f, 22.0f, 14.0f, 21.1f, 14.0f, 20.0f)
                horizontalLineTo(10.0f)
                curveTo(10.0f, 21.1f, 10.9f, 22.0f, 12.0f, 22.0f)
                close()

                moveTo(18.0f, 16.0f)
                verticalLineTo(11.0f)
                curveTo(18.0f, 7.93f, 16.36f, 5.36f, 13.5f, 4.68f)
                verticalLineTo(4.0f)
                curveTo(13.5f, 3.17f, 12.83f, 2.5f, 12.0f, 2.5f)
                curveTo(11.17f, 2.5f, 10.5f, 3.17f, 10.5f, 4.0f)
                verticalLineTo(4.68f)
                curveTo(7.63f, 5.36f, 6.0f, 7.92f, 6.0f, 11.0f)
                verticalLineTo(16.0f)
                lineTo(4.0f, 18.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(20.0f)
                verticalLineTo(18.0f)
                lineTo(18.0f, 16.0f)
                close()

                // Sound waves
                moveTo(19.5f, 8.0f)
                curveTo(19.5f, 6.0f, 18.5f, 4.3f, 17.0f, 3.3f)
                lineTo(16.2f, 4.7f)
                curveTo(17.2f, 5.4f, 17.9f, 6.6f, 17.9f, 8.0f)
                curveTo(17.9f, 9.4f, 17.2f, 10.6f, 16.2f, 11.3f)
                lineTo(17.0f, 12.7f)
                curveTo(18.5f, 11.7f, 19.5f, 10.0f, 19.5f, 8.0f)
                close()

                moveTo(4.5f, 8.0f)
                curveTo(4.5f, 10.0f, 5.5f, 11.7f, 7.0f, 12.7f)
                lineTo(7.8f, 11.3f)
                curveTo(6.8f, 10.6f, 6.1f, 9.4f, 6.1f, 8.0f)
                curveTo(6.1f, 6.6f, 6.8f, 5.4f, 7.8f, 4.7f)
                lineTo(7.0f, 3.3f)
                curveTo(5.5f, 4.3f, 4.5f, 6.0f, 4.5f, 8.0f)
                close()
            }
        }
        return _animatedBell!!
    }

private var _animatedBell: ImageVector? = null

@Composable
fun AnimatedBellIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    isAnimating: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "bell_animation")

    val rotation by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bell_rotation"
    )

    Canvas(modifier = modifier.size(24.dp)) {
        rotate(if (isAnimating) rotation else 0f) {
            drawBell(color)
        }
    }
}

private fun DrawScope.drawBell(color: Color) {
    val width = size.width
    val height = size.height

    // Bell body path
    drawPath(
        path = androidx.compose.ui.graphics.Path().apply {
            // Bell outline
            moveTo(width * 0.5f, height * 0.1f)
            cubicTo(
                width * 0.3f, height * 0.1f,
                width * 0.15f, height * 0.3f,
                width * 0.15f, height * 0.55f
            )
            lineTo(width * 0.15f, height * 0.7f)
            lineTo(width * 0.1f, height * 0.8f)
            lineTo(width * 0.9f, height * 0.8f)
            lineTo(width * 0.85f, height * 0.7f)
            lineTo(width * 0.85f, height * 0.55f)
            cubicTo(
                width * 0.85f, height * 0.3f,
                width * 0.7f, height * 0.1f,
                width * 0.5f, height * 0.1f
            )
            close()

            // Bell clapper
            moveTo(width * 0.5f, height * 0.85f)
            lineTo(width * 0.45f, height * 0.95f)
            lineTo(width * 0.55f, height * 0.95f)
            close()
        },
        color = color
    )
}
