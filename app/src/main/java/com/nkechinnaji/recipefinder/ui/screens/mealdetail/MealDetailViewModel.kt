package com.nkechinnaji.recipefinder.ui.screens.mealdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkechinnaji.recipefinder.data.model.Meal
import com.nkechinnaji.recipefinder.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MealDetailUiState(
    val meal: Meal? = null,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

class MealDetailViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MealDetailUiState())
    val uiState: StateFlow<MealDetailUiState> = _uiState.asStateFlow()
    
    private var currentMealId: String? = null
    
    fun loadMeal(mealId: String) {
        if (currentMealId == mealId && _uiState.value.meal != null) return
        
        currentMealId = mealId
        _uiState.value = MealDetailUiState(isLoading = true)
        
        viewModelScope.launch {
            // Load favorite status
            repository.isFavorite(mealId).collect { isFav ->
                _uiState.value = _uiState.value.copy(isFavorite = isFav)
            }
        }
        
        viewModelScope.launch {
            repository.getMealById(mealId)
                .onSuccess { meal ->
                    _uiState.value = _uiState.value.copy(
                        meal = meal,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load meal"
                    )
                }
        }
    }
    
    fun toggleFavorite() {
        val meal = _uiState.value.meal ?: return
        
        viewModelScope.launch {
            if (_uiState.value.isFavorite) {
                repository.removeFavorite(meal.id)
            } else {
                repository.addFavorite(meal)
            }
        }
    }
}
