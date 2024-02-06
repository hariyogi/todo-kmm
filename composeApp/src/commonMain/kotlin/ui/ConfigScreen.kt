@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import themes.ColorSchemeSelection
import viewmodel.AppConfigViewModel
import viewmodel.state.ThemeType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val appConfigVM = koinViewModel<AppConfigViewModel>()
    val appConfigState by appConfigVM.uiState.collectAsState()

    val cScope = rememberCoroutineScope()

    var selectedColorScheme by remember { mutableStateOf(appConfigState.selectedColorScheme) }
    var selectedThemeType by remember { mutableStateOf(appConfigState.themeType) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Configuration") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navigator.goBack()
                        }
                    ) {
                        Icon(Icons.Rounded.ArrowBack, null)
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            // Save configuration to database and update view model
                            cScope.launch(Dispatchers.IO) {
                                appConfigVM.updateColorScheme(selectedColorScheme)
                                appConfigVM.updateThemeType(selectedThemeType)
                            }
                            navigator.goBack()
                        }
                    ) {
                        Text("Simpan")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(top = it.calculateTopPadding() + 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(text = "Theme", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(16.dp))
            ConfigItem(
                title = "Color",
                selectedItem = selectedColorScheme,
                itemsToSelect = ColorSchemeSelection.entries,
                labelMapping = { item -> item.viewName },
                onItemSelected = { item -> selectedColorScheme = item },
                modifier = Modifier.padding(start = 16.dp)
            )
            ConfigItem(
                title = "Dark Theme",
                selectedItem = selectedThemeType,
                itemsToSelect = ThemeType.entries,
                labelMapping = { item -> item.name.lowercase() },
                onItemSelected = { item -> selectedThemeType = item },
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> ConfigItem(
    title: String,
    selectedItem: T,
    itemsToSelect: List<T>,
    onItemSelected: (item: T) -> Unit,
    labelMapping: (T) -> String = { it.toString() },
    modifier: Modifier = Modifier
) {
    var openBottomSheet by remember { mutableStateOf(false) }

    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false },
            modifier = Modifier
                .fillMaxHeight()
        ){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                items(itemsToSelect) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                onItemSelected(it)
                                openBottomSheet = false
                            }
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = labelMapping(it),
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { openBottomSheet = true }
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 8.dp)
        )
        Text(
            text = labelMapping(selectedItem),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(end = 8.dp)
        )
    }
}