package viewmodel.state

import database.dto.CategoryDto

data class CategoryBsState(
    val selectedCategory: CategoryDto? = null,
    val categories: List<CategoryDto> = listOf()
)
