package ru.tdpyramid.gemologist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Emerald40,
    secondary = Slate40,
    tertiary = Ruby40,
)

private val DarkColorScheme = darkColorScheme(
    primary = Emerald80,
    secondary = Slate80,
    tertiary = Ruby80,
)

@Composable
fun GemologistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content,
    )
}
