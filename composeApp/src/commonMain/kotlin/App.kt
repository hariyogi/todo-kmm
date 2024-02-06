@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.koin.koinViewModel
import themes.MyTheme
import ui.AppNavigation
import viewmodel.AppConfigViewModel

@Composable
fun App() {
    PreComposeApp {
        val appConfigVM = koinViewModel<AppConfigViewModel>()
        val appConfigState by appConfigVM.uiState.collectAsState()
        MyTheme(appConfigState) {
            AppNavigation()
        }
    }
}