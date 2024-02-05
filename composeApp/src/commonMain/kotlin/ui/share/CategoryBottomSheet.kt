@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package ui.share

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import database.dto.CategoryDto
import moe.tlaster.precompose.koin.koinViewModel
import toColor
import viewmodel.CategoryBsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    onDismissRequest: () -> Unit,
    onAddNewCategory: () -> Unit,
    onItemSelect: (item: CategoryDto) -> Unit,
    modifier: Modifier = Modifier,
) {

    val catBsViewModel = koinViewModel<CategoryBsViewModel>()
    val uiState by catBsViewModel.uiState.collectAsState()


    if (uiState.categories.isEmpty()) {
        LaunchedEffect(uiState.categories) {
            catBsViewModel.fillCategories()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            HeaderSheet(onDismissRequest, onAddNewCategory)
            CategoryItemSheet(
                uiState.categories,
                onItemSelect = { onItemSelect(it) },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)

            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategoryItemSheet(
    items: List<CategoryDto>,
    onItemSelect: (item: CategoryDto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(items) { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onItemSelect(item) }
            ) {
                Circle(
                    width = 15.dp,
                    height = 15.dp,
                    color = item.color?.toColor() ?: Color.Transparent
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(item.desc)
                }
            }
        }
    }
}

@Composable
private fun HeaderSheet(
    onDismissRequest: () -> Unit,
    onAddNewCategory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = onDismissRequest) {
            Icon(Icons.Rounded.Close, null)
        }
        TextButton(onClick = {
            onAddNewCategory()
        }) {
            Icon(Icons.Rounded.Add, null)
            Spacer(Modifier.width(8.dp))
            Text("Tambah")
        }
    }
}