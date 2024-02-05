package viewmodel

import database.dto.CategoryDto
import database.repo.CategoryRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import java.time.LocalDateTime

class CategoryBsViewModel(
    private val categoryRepo: CategoryRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryBsState())
    val uiState = _uiState.asStateFlow()

    suspend fun fillCategories() {
        val newCategories = categoryRepo.findAll()
        _uiState.update {
            it.copy(
                categories = newCategories.await()
            )
        }
    }


    suspend fun addAndSelectCategory(title: String, description: String, color: String?) {
        println("Calling addAndSelectCategory suspend method")
        val id = categoryRepo.create(title, description, color).await()
        val selected = CategoryDto(
            id = id,
            name = title,
            desc = description,
            color = color,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        _uiState.update {
            it.copy(
                selectedCategory = selected
            )
        }
//        fillCategories()
    }


    fun setSelectedCategory(category: CategoryDto) {
        _uiState.update {
            it.copy(
                selectedCategory = category
            )
        }
    }

    fun resetSelectedCategory() {
        _uiState.update {
            it.copy(
                selectedCategory = null
            )
        }
    }

}