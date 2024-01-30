package ui

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun AppNavigation() {
    val navigator = rememberNavigator()
    NavHost(
        navigator = navigator,
        initialRoute = "/splash"
    ) {
        scene(
            route = "/splash"
        ) {
            SplashScreen(navigator)
        }

        scene(
            route = "/main"
        ) {
            MainScreen(navigator)
        }

        scene(
            route = "/input"
        ) {
            InputScreen(navigator)
        }
    }
}