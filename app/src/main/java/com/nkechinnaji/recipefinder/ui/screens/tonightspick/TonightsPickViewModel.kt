package com.nkechinnaji.recipefinder.ui.screens.tonightspick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkechinnaji.recipefinder.data.local.FavoriteEntity
import com.nkechinnaji.recipefinder.data.model.Category
import com.nkechinnaji.recipefinder.data.model.Meal
import com.nkechinnaji.recipefinder.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TonightsPickUiState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: String? = null,
    val pickFromFavoritesOnly: Boolean = false,
    val favoritesCount: Int = 0,
    val pickedMeal: Meal? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class TonightsPickViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TonightsPickUiState())
    val uiState: StateFlow<TonightsPickUiState> = _uiState.asStateFlow()
    
    init {
        loadCategories()
        loadFavoritesCount()
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            repository.getCategories()
                .onSuccess { categories ->
                    _uiState.value = _uiState.value.copy(categories = categories)
                }
        }
    }
    
    private fun loadFavoritesCount() {
        viewModelScope.launch {
            repository.getFavoritesCount().collect { count ->
                _uiState.value = _uiState.value.copy(favoritesCount = count)
            }
        }
    }
    
    fun updateSelectedCategory(category: String?) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }
    
    fun updatePickFromFavorites(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(pickFromFavoritesOnly = enabled)
    }
    
    fun getTonightsPick() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            if (_uiState.value.pickFromFavoritesOnly) {
                // Pick from favorites
                val favorites = repository.getAllFavoritesList()
                val filteredFavorites = if (_uiState.value.selectedCategory != null) {
                    favorites.filter { it.category == _uiState.value.selectedCategory }
                } else {
                    favorites
                }
                
                if (filteredFavorites.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "No favorites found matching your criteria"
                    )
                    return@launch
                }
                
                val randomFavorite = filteredFavorites.random()
                repository.getMealById(randomFavorite.id)
                    .onSuccess { meal ->
                        _uiState.value = _uiState.value.copy(
                            pickedMeal = meal,
                            isLoading = false
                        )
                    }
                    .onFailure { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to get meal details"
                        )
                    }
            } else {
                // Pick from all meals
                val category = _uiState.value.selectedCategory
                
                if (category != null) {
                    // Get meals from category and pick random
                    repository.getMealsByCategory(category)
                        .onSuccess { meals ->
                            if (meals.isEmpty()) {
                                _uiState.value = _uiState.value.copy(
                                    isLoading = false,
                                    error = "No meals found in this category"
                                )
                                return@onSuccess
                            }
                            
                            val randomMeal = meals.random()
                            repository.getMealById(randomMeal.id)
                                .onSuccess { meal ->
                                    _uiState.value = _uiState.value.copy(
                                        pickedMeal = meal,
                                        isLoading = false
                                    )
                                }
                                .onFailure { error ->
                                    _uiState.value = _uiState.value.copy(
                                        isLoading = false,
                                        error = error.message ?: "Failed to get meal details"
                                    )
                                }
                        }
                        .onFailure { error ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "Failed to get meals"
                            )
                        }
                } else {
                    // Get random meal
                    repository.getRandomMeal()
                        .onSuccess { meal ->
                            _uiState.value = _uiState.value.copy(
                                pickedMeal = meal,
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
        }
    }
    
    fun clearPick() {
        _uiState.value = _uiState.value.copy(pickedMeal = null)
    }
}
