package themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import themes.color.CustomColorScheme
import themes.color.blues.bluesColorScheme
import themes.color.femin.feminColorScheme
import themes.color.night.nightColorScheme
import viewmodel.state.AppConfigState
import viewmodel.state.ThemeType

enum class ColorSchemeSelection(
    val colorScheme: CustomColorScheme,
    val viewName: String
) {
    FEMIN(feminColorScheme, "Feminin"),
    BLUES(bluesColorScheme, "Blues Sky"),
    NIGHT(nightColorScheme, "Night City")
}

@Composable
fun MyTheme(
    config: AppConfigState,
    content: @Composable() () -> Unit
) {
    val darkTheme = config.themeType == ThemeType.DARK
            || (config.themeType == ThemeType.SYSTEM && isSystemInDarkTheme())

    val colors = if (darkTheme) {
        config.selectedColorScheme.colorScheme.darkColorScheme
    }else {
        config.selectedColorScheme.colorScheme.lightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        content = content
    )
}