package br.com.iftm.edu.nostresswedding.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Pink40,
    onPrimary = Color.Black,
    primaryContainer = Pink20,
    onPrimaryContainer = Color.White,

    secondary = PastelYellow40,
    onSecondary = Color.Black,
    secondaryContainer = PastelYellow20,
    onSecondaryContainer = Color.White,

    tertiary = Pink80,
    onTertiary = Color.Black,
    tertiaryContainer = Pink20,
    onTertiaryContainer = Color.White,

    background = SurfaceDim,
    onBackground = Neutral90,
    surface = SurfaceContainer,
    onSurface = Neutral90,
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),

    outline = Outline,
    inverseSurface = Neutral90,
    inverseOnSurface = SurfaceDim,
    inversePrimary = Pink80
)


private val LightColorScheme = lightColorScheme(
    primary = Pink40,
    onPrimary = Color.White,
    primaryContainer = Pink80,
    onPrimaryContainer = Color.Black,

    secondary = PastelYellow40,
    onSecondary = Color.Black,
    secondaryContainer = PastelYellow80,
    onSecondaryContainer = Color.Black,

    tertiary = Pink20,
    onTertiary = Color.White,
    tertiaryContainer = Pink80,
    onTertiaryContainer = Color.Black,

    background = SurfaceBright,
    onBackground = Neutral10,
    surface = Color(0xFFFFF8F5),
    onSurface = Neutral10,
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = Color(0xFF49454F),

    outline = Outline,
    inverseSurface = SurfaceDim,
    inverseOnSurface = Neutral90,
    inversePrimary = Pink20
)

@Composable
fun NoStressWeddingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}