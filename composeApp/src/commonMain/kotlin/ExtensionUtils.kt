import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    val colorHex = "ff" + this.removePrefix("#").lowercase()
    return Color(colorHex.toLong(16))
}

fun WindowSizeClass.isCompact() = this.widthSizeClass == WindowWidthSizeClass.Compact
fun WindowSizeClass.isExpanded() = this.widthSizeClass == WindowWidthSizeClass.Expanded
fun WindowSizeClass.isMedium() = this.widthSizeClass == WindowWidthSizeClass.Medium