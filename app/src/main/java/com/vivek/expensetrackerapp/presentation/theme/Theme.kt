
package com.vivek.expensetrackerapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorPalette = darkColorScheme(
    primary = Color.White,
    onPrimary = WhiteColorVariant,

    background = BlueColor,
    onBackground = BlueColorVariant,


    surface = LightBlueColor,


    )

private val LightColorPalette = lightColorScheme(
    //text
    primary = Color.Black,
    onPrimary = BlackColorVariant,


    //background
    background = Color.White,
    onBackground = WhiteColorVariant,

    surface = LightBlueColor,


    )

@Composable
fun ExpenseTrackerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}