package database.dto

import id.harlabs.delight.gen.Config

object ConfigField {
    const val TABLE = "config"
    const val KEY = "key"
    const val VALUE = "value_"
}

fun Config.toDto() = ConfigDto(
    key = this.key,
    value = this.value_
)

data class ConfigDto(
    val key: String,
    val value: String
)