package com.example.kindspark.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Outlined.CustomPalette: ImageVector
    get() {
        if (_customPalette != null) {
            return _customPalette!!
        }
        _customPalette = materialIcon(name = "CustomPalette") {
            materialPath {
                moveTo(12.0f, 3.0f)
                curveTo(16.97f, 3.0f, 21.0f, 7.03f, 21.0f, 12.0f)
                curveTo(21.0f, 13.66f, 20.33f, 15.64f, 19.0f, 16.97f)
                curveTo(17.66f, 18.31f, 15.66f, 19.0f, 13.0f, 19.0f)
                horizontalLineTo(9.5f)
                curveTo(8.67f, 19.0f, 8.0f, 18.33f, 8.0f, 17.5f)
                curveTo(8.0f, 16.67f, 8.67f, 16.0f, 9.5f, 16.0f)
                horizontalLineTo(13.0f)
                curveTo(14.66f, 16.0f, 16.0f, 14.66f, 16.0f, 13.0f)
                curveTo(16.0f, 11.34f, 14.66f, 10.0f, 13.0f, 10.0f)
                horizontalLineTo(12.0f)
                curveTo(7.03f, 10.0f, 3.0f, 6.97f, 3.0f, 2.0f)
                close()

                // Color dots
                moveTo(9.5f, 9.0f)
                curveTo(10.33f, 9.0f, 11.0f, 8.33f, 11.0f, 7.5f)
                curveTo(11.0f, 6.67f, 10.33f, 6.0f, 9.5f, 6.0f)
                curveTo(8.67f, 6.0f, 8.0f, 6.67f, 8.0f, 7.5f)
                curveTo(8.0f, 8.33f, 8.67f, 9.0f, 9.5f, 9.0f)
                close()

                moveTo(14.5f, 9.0f)
                curveTo(15.33f, 9.0f, 16.0f, 8.33f, 16.0f, 7.5f)
                curveTo(16.0f, 6.67f, 15.33f, 6.0f, 14.5f, 6.0f)
                curveTo(13.67f, 6.0f, 13.0f, 6.67f, 13.0f, 7.5f)
                curveTo(13.0f, 8.33f, 13.67f, 9.0f, 14.5f, 9.0f)
                close()

                moveTo(7.5f, 12.0f)
                curveTo(8.33f, 12.0f, 9.0f, 11.33f, 9.0f, 10.5f)
                curveTo(9.0f, 9.67f, 8.33f, 9.0f, 7.5f, 9.0f)
                curveTo(6.67f, 9.0f, 6.0f, 9.67f, 6.0f, 10.5f)
                curveTo(6.0f, 11.33f, 6.67f, 12.0f, 7.5f, 12.0f)
                close()

                moveTo(16.5f, 12.0f)
                curveTo(17.33f, 12.0f, 18.0f, 11.33f, 18.0f, 10.5f)
                curveTo(18.0f, 9.67f, 17.33f, 9.0f, 16.5f, 9.0f)
                curveTo(15.67f, 9.0f, 15.0f, 9.67f, 15.0f, 10.5f)
                curveTo(15.0f, 11.33f, 15.67f, 12.0f, 16.5f, 12.0f)
                close()
            }
        }
        return _customPalette!!
    }

private var _customPalette: ImageVector? = null

public val Icons.Outlined.CustomLandscape: ImageVector
    get() {
        if (_customLandscape != null) {
            return _customLandscape!!
        }
        _customLandscape = materialIcon(name = "CustomLandscape") {
            materialPath {
                moveTo(14.0f, 6.0f)
                lineTo(10.25f, 11.0f)
                lineTo(13.25f, 15.0f)
                horizontalLineTo(5.0f)
                lineTo(8.5f, 10.0f)
                lineTo(6.5f, 7.0f)
                lineTo(9.0f, 3.0f)
                close()

                moveTo(21.5f, 11.0f)
                lineTo(18.5f, 15.0f)
                horizontalLineTo(15.5f)
                lineTo(19.0f, 10.0f)
                close()

                // Ground line
                moveTo(2.0f, 19.0f)
                horizontalLineTo(22.0f)
                verticalLineTo(21.0f)
                horizontalLineTo(2.0f)
                close()
            }
        }
        return _customLandscape!!
    }

private var _customLandscape: ImageVector? = null

public val Icons.Outlined.CustomAutoAwesome: ImageVector
    get() {
        if (_customAutoAwesome != null) {
            return _customAutoAwesome!!
        }
        _customAutoAwesome = materialIcon(name = "CustomAutoAwesome") {
            materialPath {
                // Main star
                moveTo(19.0f, 9.0f)
                lineTo(20.25f, 6.25f)
                lineTo(23.0f, 5.0f)
                lineTo(20.25f, 3.75f)
                lineTo(19.0f, 1.0f)
                lineTo(17.75f, 3.75f)
                lineTo(15.0f, 5.0f)
                lineTo(17.75f, 6.25f)
                close()

                // Secondary star
                moveTo(19.0f, 15.0f)
                lineTo(17.75f, 17.75f)
                lineTo(15.0f, 19.0f)
                lineTo(17.75f, 20.25f)
                lineTo(19.0f, 23.0f)
                lineTo(20.25f, 20.25f)
                lineTo(23.0f, 19.0f)
                lineTo(20.25f, 17.75f)
                close()

                // Left star
                moveTo(11.5f, 9.5f)
                lineTo(10.0f, 6.0f)
                lineTo(6.5f, 4.5f)
                lineTo(10.0f, 3.0f)
                lineTo(11.5f, -0.5f)
                lineTo(13.0f, 3.0f)
                lineTo(16.5f, 4.5f)
                lineTo(13.0f, 6.0f)
                close()

                // Small sparkle
                moveTo(7.0f, 14.0f)
                lineTo(6.0f, 11.5f)
                lineTo(3.5f, 10.5f)
                lineTo(6.0f, 9.5f)
                lineTo(7.0f, 7.0f)
                lineTo(8.0f, 9.5f)
                lineTo(10.5f, 10.5f)
                lineTo(8.0f, 11.5f)
                close()
            }
        }
        return _customAutoAwesome!!
    }

private var _customAutoAwesome: ImageVector? = null

public val Icons.Outlined.CustomSchedule: ImageVector
    get() {
        if (_customSchedule != null) {
            return _customSchedule!!
        }
        _customSchedule = materialIcon(name = "CustomSchedule") {
            materialPath {
                // Clock circle
                moveTo(12.0f, 2.0f)
                curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                curveTo(2.0f, 17.52f, 6.48f, 22.0f, 12.0f, 22.0f)
                curveTo(17.52f, 22.0f, 22.0f, 17.52f, 22.0f, 12.0f)
                curveTo(22.0f, 6.48f, 17.52f, 2.0f, 12.0f, 2.0f)
                close()

                moveTo(12.0f, 20.0f)
                curveTo(7.59f, 20.0f, 4.0f, 16.41f, 4.0f, 12.0f)
                curveTo(4.0f, 7.59f, 7.59f, 4.0f, 12.0f, 4.0f)
                curveTo(16.41f, 4.0f, 20.0f, 7.59f, 20.0f, 12.0f)
                curveTo(20.0f, 16.41f, 16.41f, 20.0f, 12.0f, 20.0f)
                close()

                // Clock hands
                moveTo(12.5f, 7.0f)
                horizontalLineTo(11.0f)
                verticalLineTo(13.0f)
                lineTo(16.25f, 16.15f)
                lineTo(17.0f, 14.92f)
                lineTo(12.5f, 12.25f)
                close()

                // Spark trail
                moveTo(19.5f, 3.5f)
                lineTo(18.5f, 2.5f)
                lineTo(20.5f, 0.5f)
                lineTo(21.5f, 1.5f)
                close()

                moveTo(21.5f, 5.5f)
                lineTo(20.5f, 4.5f)
                lineTo(23.5f, 1.5f)
                lineTo(24.5f, 2.5f)
                close()
            }
        }
        return _customSchedule!!
    }

private var _customSchedule: ImageVector? = null

public val Icons.Outlined.CustomVolumeUp: ImageVector
    get() {
        if (_customVolumeUp != null) {
            return _customVolumeUp!!
        }
        _customVolumeUp = materialIcon(name = "CustomVolumeUp") {
            materialPath {
                // Speaker cone
                moveTo(3.0f, 9.0f)
                verticalLineTo(15.0f)
                horizontalLineTo(7.0f)
                lineTo(12.0f, 20.0f)
                verticalLineTo(4.0f)
                lineTo(7.0f, 9.0f)
                horizontalLineTo(3.0f)
                close()

                // Sound waves
                moveTo(16.5f, 12.0f)
                curveTo(16.5f, 10.23f, 15.48f, 8.71f, 14.0f, 7.97f)
                verticalLineTo(16.02f)
                curveTo(15.48f, 15.29f, 16.5f, 13.77f, 16.5f, 12.0f)
                close()

                moveTo(14.0f, 3.23f)
                verticalLineTo(5.29f)
                curveTo(16.89f, 6.15f, 19.0f, 8.83f, 19.0f, 12.0f)
                curveTo(19.0f, 15.17f, 16.89f, 17.85f, 14.0f, 18.71f)
                verticalLineTo(20.77f)
                curveTo(18.01f, 19.86f, 21.0f, 16.28f, 21.0f, 12.0f)
                curveTo(21.0f, 7.72f, 18.01f, 4.14f, 14.0f, 3.23f)
                close()
            }
        }
        return _customVolumeUp!!
    }

private var _customVolumeUp: ImageVector? = null

public val Icons.Filled.CustomHistoryEdu: ImageVector
    get() {
        if (_customHistoryEdu != null) {
            return _customHistoryEdu!!
        }
        _customHistoryEdu = materialIcon(name = "CustomHistoryEdu") {
            materialPath {
                // Book/Education icon
                moveTo(19.0f, 3.0f)
                horizontalLineTo(5.0f)
                curveTo(3.89f, 3.0f, 3.0f, 3.89f, 3.0f, 5.0f)
                verticalLineTo(19.0f)
                curveTo(3.0f, 20.11f, 3.89f, 21.0f, 5.0f, 21.0f)
                horizontalLineTo(19.0f)
                curveTo(20.11f, 21.0f, 21.0f, 20.11f, 21.0f, 19.0f)
                verticalLineTo(5.0f)
                curveTo(21.0f, 3.89f, 20.11f, 3.0f, 19.0f, 3.0f)
                close()

                moveTo(19.0f, 19.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(5.0f)
                horizontalLineTo(19.0f)
                verticalLineTo(19.0f)
                close()

                // Clock/History element
                moveTo(12.0f, 7.0f)
                curveTo(9.24f, 7.0f, 7.0f, 9.24f, 7.0f, 12.0f)
                curveTo(7.0f, 14.76f, 9.24f, 17.0f, 12.0f, 17.0f)
                curveTo(14.76f, 17.0f, 17.0f, 14.76f, 17.0f, 12.0f)
                curveTo(17.0f, 9.24f, 14.76f, 7.0f, 12.0f, 7.0f)
                close()

                // Clock hands
                moveTo(12.5f, 9.0f)
                horizontalLineTo(11.5f)
                verticalLineTo(12.5f)
                lineTo(14.0f, 14.0f)
                lineTo(14.5f, 13.2f)
                lineTo(12.5f, 12.0f)
                verticalLineTo(9.0f)
                close()
            }
        }
        return _customHistoryEdu!!
    }

private var _customHistoryEdu: ImageVector? = null

// Simple History Navigation Icon
public val Icons.Filled.HistoryNav: ImageVector
    get() {
        if (_historyNav != null) {
            return _historyNav!!
        }
        _historyNav = materialIcon(name = "HistoryNav") {
            materialPath {
                // Simple book icon
                moveTo(18.0f, 2.0f)
                horizontalLineTo(6.0f)
                curveTo(4.9f, 2.0f, 4.0f, 2.9f, 4.0f, 4.0f)
                verticalLineTo(20.0f)
                curveTo(4.0f, 21.1f, 4.9f, 22.0f, 6.0f, 22.0f)
                horizontalLineTo(18.0f)
                curveTo(19.1f, 22.0f, 20.0f, 21.1f, 20.0f, 20.0f)
                verticalLineTo(4.0f)
                curveTo(20.0f, 2.9f, 19.1f, 2.0f, 18.0f, 2.0f)
                close()

                moveTo(18.0f, 20.0f)
                horizontalLineTo(6.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(20.0f)
                close()

                // Page lines
                moveTo(8.0f, 6.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(8.0f)
                horizontalLineTo(8.0f)
                close()

                moveTo(8.0f, 10.0f)
                horizontalLineTo(16.0f)
                verticalLineTo(12.0f)
                horizontalLineTo(8.0f)
                close()

                moveTo(8.0f, 14.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(16.0f)
                horizontalLineTo(8.0f)
                close()
            }
        }
        return _historyNav!!
    }

private var _historyNav: ImageVector? = null

// Custom List Icon for Navigation
public val Icons.Filled.CustomList: ImageVector
    get() {
        if (_customList != null) {
            return _customList!!
        }
        _customList = materialIcon(name = "CustomList") {
            materialPath {
                // List items
                moveTo(3.0f, 5.0f)
                horizontalLineTo(21.0f)
                verticalLineTo(7.0f)
                horizontalLineTo(3.0f)
                close()

                moveTo(3.0f, 11.0f)
                horizontalLineTo(21.0f)
                verticalLineTo(13.0f)
                horizontalLineTo(3.0f)
                close()

                moveTo(3.0f, 17.0f)
                horizontalLineTo(21.0f)
                verticalLineTo(19.0f)
                horizontalLineTo(3.0f)
                close()

                // Optional bullet points
                moveTo(1.0f, 5.0f)
                curveTo(1.55f, 5.0f, 2.0f, 5.45f, 2.0f, 6.0f)
                curveTo(2.0f, 6.55f, 1.55f, 7.0f, 1.0f, 7.0f)
                curveTo(0.45f, 7.0f, 0.0f, 6.55f, 0.0f, 6.0f)
                curveTo(0.0f, 5.45f, 0.45f, 5.0f, 1.0f, 5.0f)
                close()

                moveTo(1.0f, 11.0f)
                curveTo(1.55f, 11.0f, 2.0f, 11.45f, 2.0f, 12.0f)
                curveTo(2.0f, 12.55f, 1.55f, 13.0f, 1.0f, 13.0f)
                curveTo(0.45f, 13.0f, 0.0f, 12.55f, 0.0f, 12.0f)
                curveTo(0.0f, 11.45f, 0.45f, 11.0f, 1.0f, 11.0f)
                close()

                moveTo(1.0f, 17.0f)
                curveTo(1.55f, 17.0f, 2.0f, 17.45f, 2.0f, 18.0f)
                curveTo(2.0f, 18.55f, 1.55f, 19.0f, 1.0f, 19.0f)
                curveTo(0.45f, 19.0f, 0.0f, 18.55f, 0.0f, 18.0f)
                curveTo(0.0f, 17.45f, 0.45f, 17.0f, 1.0f, 17.0f)
                close()
            }
        }
        return _customList!!
    }

private var _customList: ImageVector? = null

// Custom Error Icon
public val Icons.Filled.CustomErrorIcon: ImageVector
    get() {
        if (_customErrorIcon != null) {
            return _customErrorIcon!!
        }
        _customErrorIcon = materialIcon(name = "CustomErrorIcon") {
            materialPath {
                // Error circle background
                moveTo(12.0f, 2.0f)
                curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                curveTo(2.0f, 17.52f, 6.48f, 22.0f, 12.0f, 22.0f)
                curveTo(17.52f, 22.0f, 22.0f, 17.52f, 22.0f, 12.0f)
                curveTo(22.0f, 6.48f, 17.52f, 2.0f, 12.0f, 2.0f)
                close()

                // Exclamation mark body
                moveTo(11.0f, 7.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(13.0f)
                horizontalLineTo(11.0f)
                close()

                // Exclamation mark dot
                moveTo(11.0f, 15.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(17.0f)
                horizontalLineTo(11.0f)
                close()
            }
        }
        return _customErrorIcon!!
    }

private var _customErrorIcon: ImageVector? = null
