package com.example.kindspark.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import com.example.kindspark.R

private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

// Define our calming font families
private val MerriweatherFont = GoogleFont("Merriweather")
private val CaveatFont = GoogleFont("Caveat")
private val QuicksandFont = GoogleFont("Quicksand")
private val InterFont = GoogleFont("Inter")

// Create FontFamily objects with the fonts
val MerriweatherFamily = FontFamily(
    Font(googleFont = MerriweatherFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = MerriweatherFont, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = MerriweatherFont, fontProvider = provider, weight = FontWeight.Light),
    Font(googleFont = MerriweatherFont, fontProvider = provider, weight = FontWeight.Medium)
)

val CaveatFamily = FontFamily(
    Font(googleFont = CaveatFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = CaveatFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = CaveatFont, fontProvider = provider, weight = FontWeight.Bold)
)

val QuicksandFamily = FontFamily(
    Font(googleFont = QuicksandFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = QuicksandFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = QuicksandFont, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = QuicksandFont, fontProvider = provider, weight = FontWeight.Light)
)

val InterFamily = FontFamily(
    Font(googleFont = InterFont, fontProvider = provider, weight = FontWeight.Normal),
    Font(googleFont = InterFont, fontProvider = provider, weight = FontWeight.Medium),
    Font(googleFont = InterFont, fontProvider = provider, weight = FontWeight.Bold),
    Font(googleFont = InterFont, fontProvider = provider, weight = FontWeight.Light)
)

// Typography with our custom fonts
val KindSparkTypography = Typography(
    // Use Merriweather for headings
    displayLarge = TextStyle(
        fontFamily = MerriweatherFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = MerriweatherFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = TextStyle(
        fontFamily = MerriweatherFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),

    // Use Merriweather for heading text
    headlineLarge = TextStyle(
        fontFamily = MerriweatherFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MerriweatherFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = MerriweatherFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),

    // Use Quicksand for titles
    titleLarge = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.1.sp
    ),
    titleSmall = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),

    // Use Quicksand for body text
    bodyLarge = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = QuicksandFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),

    // Use Caveat for labels to add a friendly, handwritten touch
    labelLarge = TextStyle(
        fontFamily = CaveatFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = CaveatFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = CaveatFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
