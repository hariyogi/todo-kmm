package database.repo

import database.dto.TodoDto

interface TodoRepo {
    fun create(summary: String, description: String, checked: Boolean): String
    fun findAll(): List<TodoDto>
}