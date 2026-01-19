package com.nkechinnaji.recipefinder.ui.screens.random

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkechinnaji.recipefinder.data.model.Meal
import com.nkechinnaji.recipefinder.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RandomUiState(
    val meal: Meal? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class RandomViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(RandomUiState())
    val uiState: StateFlow<RandomUiState> = _uiState.asStateFlow()
    
    fun getRandomMeal() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.getRandomMeal()
                .onSuccess { meal ->
                    _uiState.value = _uiState.value.copy(
                        meal = meal,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to get random meal"
                    )
                }
        }
    }
    
    fun clearMeal() {
        _uiState.value = RandomUiState()
    }
}
