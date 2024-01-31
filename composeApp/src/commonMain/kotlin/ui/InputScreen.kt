@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")
package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.repo.TodoRepo
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {

    val todoRepo = koinInject<TodoRepo>()
    val cScope = rememberCoroutineScope()

    val todoViewModel = koinViewModel<TodoViewModel>()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Create Todo") },
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
            modifier = Modifier.padding(
                top = it.calculateTopPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
        ) {
            TodoInput(
                onClick = {
                    cScope.launch {
                        todoRepo.create(
                            summary = it.summary,
                            description = it.description,
                            startAt = it.startAt,
                            endAt = it.endAt,
                            checked = false
                        ).await()
                        todoViewModel.cleanTodo()
                        navigator.goBack()
                    }
                }
            )
        }
    }

}