import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import modules.databaseConfModule
import modules.repoModules
import org.koin.compose.KoinApplication

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Todo list") {
        KoinApplication({
            modules(
                databaseConfModule,
                repoModules
            )
        }) {
            App()
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}