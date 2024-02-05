@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.dto.CategoryDto
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import ui.share.CategoryBottomSheet
import ui.share.CategoryChip
import ui.share.NoteRangePicker
import viewmodel.CategoryBsViewModel
import viewmodel.TodoInputViewModel
import java.time.LocalDateTime

data class TodoInputResult(
    val summary: String,
    val description: String,
    val startAt: LocalDateTime?,
    val endAt: LocalDateTime?,
    val category: CategoryDto?
)

data class NoteRange(val start: Long, val end: Long)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TodoInput(
    onClick: (result: TodoInputResult) -> Unit,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {

    val categoryBsViewModel = koinViewModel<CategoryBsViewModel>()
    val catBsUiState by categoryBsViewModel.uiState.collectAsState()

    val todoInputViewModel = koinViewModel<TodoInputViewModel>()
    val todoInputUiState by todoInputViewModel.uiState.collectAsState()

    // Ini berfungsi agar kursor keyboard dan kata tidak lari"an
    var summaryVal by remember { mutableStateOf(todoInputUiState.summaryValue) }
    var descriptionVal by remember { mutableStateOf(todoInputUiState.descValue) }

    var openDialog by remember { mutableStateOf(false) }
    var openCatBottomSheet by remember { mutableStateOf(false) }

    if (openDialog) {
        DatePickerDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = { }
        ) {
            NoteRangePicker(
                onConfirm = { start, end ->
                    todoInputViewModel.updateNoteRange(
                        NoteRange(start, end)
                    )
                    openDialog = false
                },
                onDismiss = { openDialog = false }
            )
        }

    }

    if (openCatBottomSheet) {
        CategoryBottomSheet(
            onDismissRequest = { openCatBottomSheet = false },
            onAddNewCategory = { navigator.navigate("/category/create") },
            onItemSelect = {
                openCatBottomSheet = false
                categoryBsViewModel.setSelectedCategory(it)
            }
        )
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = summaryVal,
            onValueChange = {
                summaryVal = it
                todoInputViewModel.updateSummaryValue(it)
            },
            label = { Text("Summary") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = descriptionVal,
            onValueChange = {
                descriptionVal = it
                todoInputViewModel.updateDescriptionValue(it)
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = todoInputUiState.startViewTime,
                onValueChange = {},
                label = { Text("Mulai dari") },
                enabled = false,
                modifier = Modifier
                    .weight(1f)
                    .clickable { openDialog = true }
            )
            Spacer(Modifier.width(8.dp))
            OutlinedTextField(
                value = todoInputUiState.endViewTime,
                onValueChange = {},
                label = { Text("Sampai") },
                enabled = false,
                modifier = Modifier
                    .weight(1f)
                    .clickable { openDialog = true }
            )
            Spacer(Modifier.width(8.dp))
        }
        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (catBsUiState.selectedCategory != null) {
                CategoryChip(
                    onClick = { categoryBsViewModel.resetSelectedCategory() },
                    category = catBsUiState.selectedCategory!!
                )
            }

            Spacer(Modifier.weight(1f))

            Button(onClick = {
                openCatBottomSheet = true
            }) {
                Icon(Icons.Rounded.Add, null)
                Spacer(Modifier.width(4.dp))
                Text("Kategori")
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                val result = TodoInputResult(
                    todoInputUiState.summaryValue,
                    todoInputUiState.descValue,
                    todoInputUiState.startDateTime,
                    todoInputUiState.endDateTime,
                    catBsUiState.selectedCategory
                )
                onClick(result)
            }
        ) {
            Text("Simpan todo list")
        }
    }
}
