package com.example.kindspark.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Outlined.SparklingHeart: ImageVector
    get() {
        if (_sparklingHeart != null) {
            return _sparklingHeart!!
        }
        _sparklingHeart = materialIcon(name = "SparklingHeart") {
            materialPath {
                // Main heart shape
                moveTo(12.0f, 21.35f)
                lineTo(10.55f, 20.03f)
                curveTo(5.4f, 15.36f, 2.0f, 12.27f, 2.0f, 8.5f)
                curveTo(2.0f, 5.42f, 4.42f, 3.0f, 7.5f, 3.0f)
                curveTo(9.24f, 3.0f, 10.91f, 3.81f, 12.0f, 5.08f)
                curveTo(13.09f, 3.81f, 14.76f, 3.0f, 16.5f, 3.0f)
                curveTo(19.58f, 3.0f, 22.0f, 5.42f, 22.0f, 8.5f)
                curveTo(22.0f, 12.27f, 18.6f, 15.36f, 13.45f, 20.03f)
                lineTo(12.0f, 21.35f)
                close()

                // Sparkles around the heart
                moveTo(6.0f, 2.0f)
                lineTo(5.5f, 0.5f)
                lineTo(4.0f, 1.0f)
                lineTo(5.5f, 1.5f)
                close()

                moveTo(18.0f, 2.0f)
                lineTo(18.5f, 0.5f)
                lineTo(20.0f, 1.0f)
                lineTo(18.5f, 1.5f)
                close()

                moveTo(2.0f, 6.0f)
                lineTo(1.5f, 4.5f)
                lineTo(0.0f, 5.0f)
                lineTo(1.5f, 5.5f)
                close()

                moveTo(22.0f, 6.0f)
                lineTo(22.5f, 4.5f)
                lineTo(24.0f, 5.0f)
                lineTo(22.5f, 5.5f)
                close()
            }
        }
        return _sparklingHeart!!
    }

private var _sparklingHeart: ImageVector? = null

public val Icons.Outlined.CloudHeart: ImageVector
    get() {
        if (_cloudHeart != null) {
            return _cloudHeart!!
        }
        _cloudHeart = materialIcon(name = "CloudHeart") {
            materialPath {
                // Cloud shape
                moveTo(19.35f, 10.04f)
                curveTo(18.67f, 6.59f, 15.64f, 4.0f, 12.0f, 4.0f)
                curveTo(9.11f, 4.0f, 6.6f, 5.64f, 5.35f, 8.04f)
                curveTo(2.34f, 8.36f, 0.0f, 10.91f, 0.0f, 14.0f)
                curveTo(0.0f, 17.31f, 2.69f, 20.0f, 6.0f, 20.0f)
                horizontalLineTo(19.0f)
                curveTo(21.76f, 20.0f, 24.0f, 17.76f, 24.0f, 15.0f)
                curveTo(24.0f, 12.36f, 21.95f, 10.22f, 19.35f, 10.04f)
                close()

                moveTo(19.0f, 18.0f)
                horizontalLineTo(6.0f)
                curveTo(3.79f, 18.0f, 2.0f, 16.21f, 2.0f, 14.0f)
                curveTo(2.0f, 11.95f, 3.53f, 10.24f, 5.56f, 10.03f)
                lineTo(6.63f, 9.92f)
                lineTo(7.13f, 8.97f)
                curveTo(8.08f, 7.14f, 9.94f, 6.0f, 12.0f, 6.0f)
                curveTo(14.62f, 6.0f, 16.88f, 7.86f, 17.39f, 10.43f)
                lineTo(17.69f, 11.93f)
                lineTo(19.22f, 12.04f)
                curveTo(20.78f, 12.14f, 22.0f, 13.45f, 22.0f, 15.0f)
                curveTo(22.0f, 16.65f, 20.65f, 18.0f, 19.0f, 18.0f)
                close()

                // Heart in the cloud
                moveTo(12.0f, 15.35f)
                lineTo(11.22f, 14.62f)
                curveTo(9.55f, 13.06f, 8.5f, 12.1f, 8.5f, 10.9f)
                curveTo(8.5f, 9.95f, 9.2f, 9.25f, 10.15f, 9.25f)
                curveTo(10.75f, 9.25f, 11.33f, 9.55f, 12.0f, 10.08f)
                curveTo(12.67f, 9.55f, 13.25f, 9.25f, 13.85f, 9.25f)
                curveTo(14.8f, 9.25f, 15.5f, 9.95f, 15.5f, 10.9f)
                curveTo(15.5f, 12.1f, 14.45f, 13.06f, 12.78f, 14.62f)
                lineTo(12.0f, 15.35f)
                close()
            }
        }
        return _cloudHeart!!
    }

private var _cloudHeart: ImageVector? = null
