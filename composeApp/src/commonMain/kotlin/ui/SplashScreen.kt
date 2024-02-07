package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import isExpanded
import isMedium
import kotlinx.coroutines.delay
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private const val TITLE = "TODO APP"
private const val SLOGAN = "Because your life absolute messy"

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun SplashScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {

    val windowSize = calculateWindowSizeClass()

    LaunchedEffect(Unit) {
        delay(1000)
        navigator.navigate("/main", NavOptions(launchSingleTop = true))
    }


    Scaffold(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (windowSize.isMedium()) {
                ExpandFlashScreen()
            } else {
                CompactFlashScreen()
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ExpandFlashScreen(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource("logo.png"),
            contentDescription = "Logo",
            modifier = Modifier
                .height(160.dp)
                .width(90.dp)
        )
        Spacer(Modifier.width(24.dp))
        Column {
            Text(
                text = "TODO APP",
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Because your life absolute messy",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CompactFlashScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource("logo.png"),
            contentDescription = "Logo",
            modifier = Modifier
                .height(160.dp * 2)
                .width(90.dp * 2)
        )
        Spacer(Modifier.width(24.dp))
        Text(
            text = "TODO APP",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Because your life absolute messy",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}