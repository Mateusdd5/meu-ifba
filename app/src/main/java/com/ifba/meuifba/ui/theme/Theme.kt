package com.ifba.meuifba.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = IFBAGreen,
    onPrimary = Color.White,
    primaryContainer = IFBAGreenDark,
    onPrimaryContainer = Color.White,

    secondary = IFBAYellow,
    onSecondary = Color.Black,
    secondaryContainer = IFBAYellowDark,
    onSecondaryContainer = Color.Black,

    tertiary = SuccessGreen,
    onTertiary = Color.White,

    error = ErrorRedLight,
    onError = Color.White,
    errorContainer = ErrorRed,
    onErrorContainer = Color.White,

    background = Gray900,
    onBackground = Color.White,

    surface = Gray800,
    onSurface = Color.White,
    surfaceVariant = Gray700,
    onSurfaceVariant = Gray300,

    outline = Gray600,
    outlineVariant = Gray700
)

private val LightColorScheme = lightColorScheme(
    primary = IFBAGreen,
    onPrimary = Color.White,
    primaryContainer = IFBAGreenLight,
    onPrimaryContainer = IFBAGreenDark,

    secondary = IFBAYellow,
    onSecondary = Color.Black,
    secondaryContainer = IFBAYellowLight,
    onSecondaryContainer = IFBAYellowDark,

    tertiary = SuccessGreen,
    onTertiary = Color.White,

    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorRedLight,
    onErrorContainer = ErrorRed,

    background = Gray50,
    onBackground = Gray900,

    surface = Color.White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray700,

    outline = Gray400,
    outlineVariant = Gray300
)

@Composable
fun MeuIFBATheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color está disponível no Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}