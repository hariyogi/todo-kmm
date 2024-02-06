package themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import themes.color.CustomColorScheme
import themes.color.blues.bluesColorScheme
import themes.color.femin.feminColorScheme
import themes.color.night.nightColorScheme
import viewmodel.state.AppConfigState
import viewmodel.state.ThemeType

expect fun appFontFamily(): FontFamily

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 36.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Medium
    ),
    titleMedium = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Medium
    ),
    titleSmall = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    ),
    labelSmall = TextStyle(
        fontFamily = appFontFamily(),
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium
    )
)

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
    } else {
        config.selectedColorScheme.colorScheme.lightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        typography = AppTypography,
        content = content
    )
}