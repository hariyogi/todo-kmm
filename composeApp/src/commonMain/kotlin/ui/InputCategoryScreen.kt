@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import toColor
import ui.share.Circle
import viewmodel.CategoryBsViewModel

enum class ColorChoice(val color: String) {
    PINK("#EF9595"),
    ORANGE("#EFB495"),
    ORANGE_LIGHT("#EFD595"),
    YELLOW("#EBEF95")
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InputCategoryScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {

    val catBsViewModel = koinViewModel<CategoryBsViewModel>()
    val cScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    var titleValue by remember { mutableStateOf("") }
    var descValue by remember { mutableStateOf("") }
    var colorChoice by remember { mutableStateOf<ColorChoice?>(null) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Buat category") },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.goBack()
                    }) {
                        Icon(Icons.Rounded.ArrowBack, null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        ) {
            OutlinedTextField(
                value = titleValue,
                onValueChange = { titleValue = it },
                label = { Text("Judul") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = descValue,
                onValueChange = { descValue = it },
                label = { Text("Deskripsi") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))
            Text(text = "Warna", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ColorChoice.entries.map { item ->
                    Spacer(Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .border(
                                BorderStroke(
                                    width = if (colorChoice == item) 2.dp else 0.dp,
                                    color = if (colorChoice == item)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        Color.Transparent
                                ),
                                shape = RoundedCornerShape(50)
                            )
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) { colorChoice = item }
                            .padding(vertical = 16.dp, horizontal = 8.dp)

                    ) {
                        Circle(
                            width = 50.dp,
                            height = 50.dp,
                            color = item.color.toColor()
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    cScope.launch {
                        catBsViewModel.addAndSelectCategory(
                            title = titleValue,
                            description = descValue,
                            color = colorChoice?.color
                        )
                        catBsViewModel.fillCategories()
                        navigator.goBack()
                    }
                }
            ) {
                Text("Simpan")
            }
        }
    }
}