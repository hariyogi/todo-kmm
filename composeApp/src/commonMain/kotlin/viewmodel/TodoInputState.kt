package viewmodel

import ui.NoteRange
import utils.toDateTimeViewMode
import utils.toLocalDateTime
import java.time.LocalDateTime

data class TodoInputState(
    val summaryValue: String = "",
    val descValue: String = "",
    val noteRange: NoteRange? = null
) {

    val startViewTime: String
        get() = noteRange?.start.toDateTimeViewMode() ?: "Tidak Ada"

    val endViewTime: String
        get() = noteRange?.end.toDateTimeViewMode() ?: "Tidak Ada"

    val startDateTime: LocalDateTime?
        get() = noteRange?.start.toLocalDateTime()

    val endDateTime: LocalDateTime?
        get() = noteRange?.end.toLocalDateTime()
}
