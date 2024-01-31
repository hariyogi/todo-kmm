package database.dto

import id.harlabs.delight.gen.Category
import java.time.LocalDateTime

object CategoryField {
    const val ID = "id"
    const val NAME = "cat_name"
    const val DESC = "cat_desc"
    const val COLOR = "cat_color"
    const val CREATED_AT = "created_at"
    const val UPDATED_AT = "updated_at"
}

fun Category.toDto() = CategoryDto(
    this.id,
    this.cat_name,
    this.cat_desc,
    this.cat_color,
    LocalDateTime.parse(this.created_at),
    LocalDateTime.parse(this.updated_at)
)

data class CategoryDto(
    val id: String,
    val name: String,
    val desc: String,
    val color: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
