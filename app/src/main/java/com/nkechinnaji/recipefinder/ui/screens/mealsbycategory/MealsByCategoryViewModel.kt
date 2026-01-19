package com.nkechinnaji.recipefinder.ui.screens.mealsbycategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkechinnaji.recipefinder.data.model.MealPreview
import com.nkechinnaji.recipefinder.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MealsByCategoryUiState(
    val category: String = "",
    val meals: List<MealPreview> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class MealsByCategoryViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MealsByCategoryUiState())
    val uiState: StateFlow<MealsByCategoryUiState> = _uiState.asStateFlow()
    
    fun loadMeals(category: String) {
        _uiState.value = _uiState.value.copy(category = category, isLoading = true, error = null)
        
        viewModelScope.launch {
            repository.getMealsByCategory(category)
                .onSuccess { meals ->
                    _uiState.value = _uiState.value.copy(
                        meals = meals,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load meals"
                    )
                }
        }
    }
}
