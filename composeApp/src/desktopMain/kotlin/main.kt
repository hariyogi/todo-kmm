import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import modules.databaseConfModule
import modules.repoModules
import org.koin.compose.KoinApplication
import viewmodel.viewModelModules

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Todo list") {
        KoinApplication({
            modules(
                databaseConfModule,
                repoModules,
                viewModelModules,
                handlerModules
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