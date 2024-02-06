@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import database.repo.TodoRepo
import isCompact
import isExpanded
import isMedium
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {

    val sqlDelightTodoRepo = koinInject<TodoRepo>()
    val todoViewModel = koinViewModel<TodoViewModel>()

    val uiState by todoViewModel.uiState.collectAsState()

    val composeScope = rememberCoroutineScope()

    val windowSize = calculateWindowSizeClass()

    if (uiState.todoList.isEmpty()) {
        LaunchedEffect(Unit) {
            composeScope.launch(Dispatchers.IO) {
                val result = sqlDelightTodoRepo.findAll()
                todoViewModel.addTodo(result.await())
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (windowSize.isCompact()) {
                MainTopBar(navigator)
            }
        },
    ) {
        Row {
            if (windowSize.isMedium() || windowSize.isExpanded()) {
                MainNavigationRail(navigator)
            }
            Column(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            ) {
                TodoList(
                    todoItems = uiState.todoList,
                    onDelete = {
                        composeScope.launch(Dispatchers.IO) {
                            sqlDelightTodoRepo.delete(it.id).await()
                            todoViewModel.deleteTodo(it)
                        }
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }

}

/**
 * Show top bar when the width size in compact
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("Todo list") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = {
                navigator.navigate("/config")
            }) {
                Icon(Icons.Rounded.Settings, null)
            }
            IconButton(onClick = {
                navigator.navigate("/input")
            }) {
                Icon(Icons.Rounded.Add, null)
            }
        },
        modifier = modifier
    )
}

@Composable
fun MainNavigationRail(
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    NavigationRail(
        header = { Text("Todo List") },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        NavigationRailItem(
            selected = false,
            onClick = { navigator.navigate("/input") },
            icon = { Icon(Icons.Rounded.Add, null) },
            label = { Text("Add Todo") }
        )
        NavigationRailItem(
            selected = false,
            onClick = { navigator.navigate("/config") },
            icon = { Icon(Icons.Rounded.Settings, null) },
            label = { Text("Konfigurasi") }
        )
    }

}