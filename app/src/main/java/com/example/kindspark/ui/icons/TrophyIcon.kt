package com.example.kindspark.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Outlined.TrophyWithConfetti: ImageVector
    get() {
        if (_trophyWithConfetti != null) {
            return _trophyWithConfetti!!
        }
        _trophyWithConfetti = materialIcon(name = "TrophyWithConfetti") {
            materialPath {
                // Trophy cup
                moveTo(12.0f, 15.0f)
                curveTo(14.21f, 15.0f, 16.0f, 13.21f, 16.0f, 11.0f)
                verticalLineTo(7.0f)
                curveTo(16.0f, 4.79f, 14.21f, 3.0f, 12.0f, 3.0f)
                curveTo(9.79f, 3.0f, 8.0f, 4.79f, 8.0f, 7.0f)
                verticalLineTo(11.0f)
                curveTo(8.0f, 13.21f, 9.79f, 15.0f, 12.0f, 15.0f)
                close()

                // Trophy handles
                moveTo(6.0f, 6.0f)
                curveTo(4.9f, 6.0f, 4.0f, 6.9f, 4.0f, 8.0f)
                verticalLineTo(9.0f)
                curveTo(4.0f, 10.1f, 4.9f, 11.0f, 6.0f, 11.0f)
                horizontalLineTo(8.0f)
                verticalLineTo(9.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(6.0f)
                close()

                moveTo(18.0f, 6.0f)
                verticalLineTo(9.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(11.0f)
                horizontalLineTo(18.0f)
                curveTo(19.1f, 11.0f, 20.0f, 10.1f, 20.0f, 9.0f)
                verticalLineTo(8.0f)
                curveTo(20.0f, 6.9f, 19.1f, 6.0f, 18.0f, 6.0f)
                close()

                // Trophy base
                moveTo(10.0f, 17.0f)
                horizontalLineTo(14.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(10.0f)
                close()

                moveTo(8.0f, 19.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(8.0f)
                close()

                // Confetti particles
                moveTo(3.0f, 3.0f)
                horizontalLineTo(4.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(3.0f)
                close()

                moveTo(20.0f, 2.0f)
                horizontalLineTo(21.0f)
                verticalLineTo(3.0f)
                horizontalLineTo(20.0f)
                close()

                moveTo(2.0f, 12.0f)
                horizontalLineTo(3.0f)
                verticalLineTo(13.0f)
                horizontalLineTo(2.0f)
                close()

                moveTo(21.0f, 13.0f)
                horizontalLineTo(22.0f)
                verticalLineTo(14.0f)
                horizontalLineTo(21.0f)
                close()

                moveTo(4.0f, 16.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(17.0f)
                horizontalLineTo(4.0f)
                close()

                moveTo(19.0f, 15.0f)
                horizontalLineTo(20.0f)
                verticalLineTo(16.0f)
                horizontalLineTo(19.0f)
                close()

                // Confetti diamonds
                moveTo(5.5f, 5.0f)
                lineTo(6.0f, 4.5f)
                lineTo(6.5f, 5.0f)
                lineTo(6.0f, 5.5f)
                close()

                moveTo(18.5f, 4.0f)
                lineTo(19.0f, 3.5f)
                lineTo(19.5f, 4.0f)
                lineTo(19.0f, 4.5f)
                close()

                moveTo(1.5f, 8.0f)
                lineTo(2.0f, 7.5f)
                lineTo(2.5f, 8.0f)
                lineTo(2.0f, 8.5f)
                close()

                moveTo(22.5f, 9.0f)
                lineTo(23.0f, 8.5f)
                lineTo(23.5f, 9.0f)
                lineTo(23.0f, 9.5f)
                close()
            }
        }
        return _trophyWithConfetti!!
    }

private var _trophyWithConfetti: ImageVector? = null

public val Icons.Outlined.AchievementStar: ImageVector
    get() {
        if (_achievementStar != null) {
            return _achievementStar!!
        }
        _achievementStar = materialIcon(name = "AchievementStar") {
            materialPath {
                // Star shape
                moveTo(12.0f, 2.0f)
                lineTo(15.09f, 8.26f)
                lineTo(22.0f, 9.27f)
                lineTo(17.0f, 14.14f)
                lineTo(18.18f, 21.02f)
                lineTo(12.0f, 17.77f)
                lineTo(5.82f, 21.02f)
                lineTo(7.0f, 14.14f)
                lineTo(2.0f, 9.27f)
                lineTo(8.91f, 8.26f)
                lineTo(12.0f, 2.0f)
                close()

                // Inner star highlight
                moveTo(12.0f, 6.0f)
                lineTo(13.5f, 9.5f)
                lineTo(17.0f, 10.0f)
                lineTo(14.5f, 12.4f)
                lineTo(15.1f, 15.9f)
                lineTo(12.0f, 14.3f)
                lineTo(8.9f, 15.9f)
                lineTo(9.5f, 12.4f)
                lineTo(7.0f, 10.0f)
                lineTo(10.5f, 9.5f)
                lineTo(12.0f, 6.0f)
                close()
            }
        }
        return _achievementStar!!
    }

private var _achievementStar: ImageVector? = null
