package com.example.kindspark.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kindspark.ui.theme.KindSparkDimensions
import kotlin.math.*

/**
 * Custom icon components for KindSpark app
 * Using custom drawn icons instead of Material icons for unique branding
 */

@Composable
fun KindSparkHeartIcon(
    modifier: Modifier = Modifier,
    size: Dp = KindSparkDimensions.IconMedium,
    color: Color = MaterialTheme.colorScheme.primary,
    filled: Boolean = false
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val heartPath = createHeartPath(this.size)

        if (filled) {
            drawPath(
                path = heartPath,
                color = color
            )
        } else {
            drawPath(
                path = heartPath,
                color = color,
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

@Composable
fun KindSparkSparkIcon(
    modifier: Modifier = Modifier,
    size: Dp = KindSparkDimensions.IconMedium,
    color: Color = MaterialTheme.colorScheme.secondary,
    animated: Boolean = false
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val center = this.size.center
        val radius = this.size.minDimension / 4

        // Draw 8-pointed spark
        for (i in 0..7) {
            val angle = (i * 45f) * PI / 180f
            val startX = center.x + cos(angle).toFloat() * radius * 0.3f
            val startY = center.y + sin(angle).toFloat() * radius * 0.3f
            val endX = center.x + cos(angle).toFloat() * radius
            val endY = center.y + sin(angle).toFloat() * radius

            drawLine(
                color = color,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Center circle
        drawCircle(
            color = color,
            radius = radius * 0.2f,
            center = center
        )
    }
}

@Composable
fun KindSparkStreakIcon(
    modifier: Modifier = Modifier,
    size: Dp = KindSparkDimensions.IconMedium,
    color: Color = MaterialTheme.colorScheme.tertiary,
    streakCount: Int = 3
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val center = this.size.center
        val flameHeight = this.size.height * 0.8f
        val flameWidth = this.size.width * 0.6f

        // Draw flame shape
        val flamePath = Path().apply {
            moveTo(center.x, center.y + flameHeight / 2)

            // Left curve
            cubicTo(
                center.x - flameWidth / 3, center.y + flameHeight / 4,
                center.x - flameWidth / 2, center.y - flameHeight / 4,
                center.x, center.y - flameHeight / 2
            )

            // Right curve
            cubicTo(
                center.x + flameWidth / 2, center.y - flameHeight / 4,
                center.x + flameWidth / 3, center.y + flameHeight / 4,
                center.x, center.y + flameHeight / 2
            )

            close()
        }

        drawPath(
            path = flamePath,
            color = color
        )

        // Add inner flame details
        val innerFlamePath = Path().apply {
            moveTo(center.x, center.y + flameHeight / 3)

            cubicTo(
                center.x - flameWidth / 6, center.y,
                center.x - flameWidth / 4, center.y - flameHeight / 6,
                center.x, center.y - flameHeight / 3
            )

            cubicTo(
                center.x + flameWidth / 4, center.y - flameHeight / 6,
                center.x + flameWidth / 6, center.y,
                center.x, center.y + flameHeight / 3
            )

            close()
        }

        drawPath(
            path = innerFlamePath,
            color = color.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun KindSparkCheckIcon(
    modifier: Modifier = Modifier,
    size: Dp = KindSparkDimensions.IconMedium,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val center = this.size.center
        val checkSize = this.size.minDimension * 0.6f

        val checkPath = Path().apply {
            moveTo(center.x - checkSize / 3, center.y)
            lineTo(center.x - checkSize / 6, center.y + checkSize / 4)
            lineTo(center.x + checkSize / 3, center.y - checkSize / 4)
        }

        drawPath(
            path = checkPath,
            color = color,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

@Composable
fun KindSparkPlusIcon(
    modifier: Modifier = Modifier,
    size: Dp = KindSparkDimensions.IconMedium,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val center = this.size.center
        val lineLength = this.size.minDimension * 0.6f
        val strokeWidth = 3.dp.toPx()

        // Horizontal line
        drawLine(
            color = color,
            start = Offset(center.x - lineLength / 2, center.y),
            end = Offset(center.x + lineLength / 2, center.y),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Vertical line
        drawLine(
            color = color,
            start = Offset(center.x, center.y - lineLength / 2),
            end = Offset(center.x, center.y + lineLength / 2),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun KindSparkSettingsIcon(
    modifier: Modifier = Modifier,
    size: Dp = KindSparkDimensions.IconMedium,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val center = this.size.center
        val gearRadius = this.size.minDimension * 0.4f
        val innerRadius = gearRadius * 0.6f
        val teethCount = 8
        val strokeWidth = 2.dp.toPx()

        // Draw gear teeth
        for (i in 0 until teethCount) {
            val angle = (i * 360f / teethCount) * PI / 180f
            val outerX = center.x + cos(angle).toFloat() * gearRadius
            val outerY = center.y + sin(angle).toFloat() * gearRadius
            val innerX = center.x + cos(angle).toFloat() * innerRadius
            val innerY = center.y + sin(angle).toFloat() * innerRadius

            drawLine(
                color = color,
                start = Offset(innerX, innerY),
                end = Offset(outerX, outerY),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }

        // Draw inner circle
        drawCircle(
            color = color,
            radius = innerRadius * 0.5f,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        // Draw outer circle
        drawCircle(
            color = color,
            radius = gearRadius,
            center = center,
            style = Stroke(width = strokeWidth)
        )
    }
}

@Composable
fun KindSparkHistoryIcon(
    modifier: Modifier = Modifier,
    size: Dp = KindSparkDimensions.IconMedium,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Canvas(
        modifier = modifier.size(size)
    ) {
        val center = this.size.center
        val clockRadius = this.size.minDimension * 0.4f
        val strokeWidth = 2.dp.toPx()

        // Draw clock circle
        drawCircle(
            color = color,
            radius = clockRadius,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        // Draw clock hands
        val hourHandLength = clockRadius * 0.5f
        val minuteHandLength = clockRadius * 0.7f

        // Hour hand (pointing to 3)
        drawLine(
            color = color,
            start = center,
            end = Offset(center.x + hourHandLength, center.y),
            strokeWidth = strokeWidth * 1.5f,
            cap = StrokeCap.Round
        )

        // Minute hand (pointing to 12)
        drawLine(
            color = color,
            start = center,
            end = Offset(center.x, center.y - minuteHandLength),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Center dot
        drawCircle(
            color = color,
            radius = strokeWidth,
            center = center
        )
    }
}

// Helper function to create heart path
private fun createHeartPath(size: androidx.compose.ui.geometry.Size): Path {
    val path = Path()
    val center = size.center
    val heartWidth = size.width * 0.8f
    val heartHeight = size.height * 0.7f

    path.moveTo(center.x, center.y + heartHeight / 3)

    // Left curve
    path.cubicTo(
        center.x - heartWidth / 2, center.y - heartHeight / 3,
        center.x - heartWidth / 4, center.y - heartHeight / 2,
        center.x, center.y
    )

    // Right curve
    path.cubicTo(
        center.x + heartWidth / 4, center.y - heartHeight / 2,
        center.x + heartWidth / 2, center.y - heartHeight / 3,
        center.x, center.y + heartHeight / 3
    )

    path.close()
    return path
}
