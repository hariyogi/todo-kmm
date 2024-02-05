package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import ui.NoteRange

class TodoInputViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(TodoInputState())
    val uiState = _uiState.asStateFlow()

    fun updateSummaryValue(value: String) {
        _uiState.update {
            it.copy(
                summaryValue = value
            )
        }
    }

    fun updateDescriptionValue(value: String) {
        _uiState.update {
            it.copy(
                descValue = value
            )
        }
    }

    fun updateNoteRange(noteRange: NoteRange?) {
        _uiState.update {
            it.copy(
                noteRange = noteRange
            )
        }
    }


}