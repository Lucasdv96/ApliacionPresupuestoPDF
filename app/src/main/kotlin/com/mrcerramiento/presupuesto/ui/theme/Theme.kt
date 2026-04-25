package com.mrcerramiento.presupuesto.ui.theme

import androidx.compose.foundation.isSystemInDarkMode
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// MR Cerramientos Colors
val MRGreen = Color(0xFFA4C639)
val MRGreenDark = Color(0xFF8AAC2B)
val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFFF5F5F5)
val DarkGray = Color(0xFF333333)
val TextGray = Color(0xFF666666)

private val LightColors = lightColorScheme(
    primary = MRGreen,
    primaryContainer = MRGreen,
    secondary = MRGreenDark,
    tertiary = TextGray,
    background = White,
    surface = LightGray,
    onPrimary = White,
    onSecondary = White,
    onBackground = DarkGray,
    onSurface = DarkGray
)

private val DarkColors = darkColorScheme(
    primary = MRGreen,
    primaryContainer = MRGreenDark,
    secondary = MRGreenDark,
    tertiary = TextGray,
    background = DarkGray,
    surface = Color(0xFF1F1F1F),
    onPrimary = DarkGray,
    onSecondary = DarkGray,
    onBackground = White,
    onSurface = White
)

@Composable
fun MRPresupuestoTheme(
    darkTheme: Boolean = isSystemInDarkMode(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PresupuestoTypography,
        content = content
    )
}
