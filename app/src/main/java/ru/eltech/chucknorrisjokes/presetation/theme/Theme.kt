package ru.eltech.chucknorrisjokes.presetation.theme

import android.view.Window
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb

private val DarkColorPalette = darkColors(
    primary = Black,
    primaryVariant = Black,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Black,
    primaryVariant = Black,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ComponentActivity.ChuckNorrisJokesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    window: Window = this.window,
    content: @Composable () -> Unit
) {
    window.statusBarColor = Black.toArgb()
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}