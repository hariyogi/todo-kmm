package viewmodel.state

import themes.ColorSchemeSelection

object ConfigName {
    const val THEME_TYPE = "themeType"
    const val COLOR_SCHEME = "colorScheme"
}

enum class ThemeType {
    LIGHT, DARK, SYSTEM
}

data class AppConfigState(
    val selectedColorScheme: ColorSchemeSelection = ColorSchemeSelection.NIGHT,
    val themeType: ThemeType = ThemeType.SYSTEM
)
