package utils

expect fun generateUUID(): String

fun Boolean.toDbColumn() = if (this) 1L else 0L

fun Long.fromDbToBool() = this > 0