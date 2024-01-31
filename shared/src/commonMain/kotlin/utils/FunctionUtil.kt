package utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

expect fun generateUUID(): String

fun Boolean.toDbColumn() = if (this) 1L else 0L

fun Long.fromDbToBool() = this > 0

fun Long?.toDateTimeViewMode(dateFormat: DateTimeFormatter? = null): String? =
    this?.toLocalDateTime()?.viewMode(dateFormat)

fun Long?.toLocalDateTime() =
    if (this != null) {
        val instant = Instant.ofEpochMilli(this)
        instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
    } else {
        null
    }

val defaultDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

fun LocalDateTime.viewMode(dateFormat: DateTimeFormatter? = null) =
    if (dateFormat != null) {
        dateFormat.format(this)
    } else {
        defaultDateFormat.format(this)
    }

fun String?.toLocalDateTime() =
    if (this != null) {
        LocalDateTime.parse(this)
    } else {
        null
    }
