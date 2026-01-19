package com.nkechinnaji.recipefinder.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nkechinnaji.recipefinder.data.model.Meal
import com.nkechinnaji.recipefinder.data.model.MealPreview
import com.nkechinnaji.recipefinder.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class SearchMode {
    BY_NAME,
    BY_INGREDIENT
}

data class SearchUiState(
    val searchQuery: String = "",
    val searchMode: SearchMode = SearchMode.BY_NAME,
    val mealResults: List<Meal> = emptyList(),
    val previewResults: List<MealPreview> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val hasSearched: Boolean = false
)

class SearchViewModel(
    private val repository: RecipeRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()
    
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
    
    fun updateSearchMode(mode: SearchMode) {
        _uiState.value = _uiState.value.copy(
            searchMode = mode,
            mealResults = emptyList(),
            previewResults = emptyList(),
            hasSearched = false,
            error = null
        )
    }
    
    fun search() {
        val query = _uiState.value.searchQuery.trim()
        if (query.isEmpty()) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            when (_uiState.value.searchMode) {
                SearchMode.BY_NAME -> {
                    repository.searchMealsByName(query)
                        .onSuccess { meals ->
                            _uiState.value = _uiState.value.copy(
                                mealResults = meals,
                                previewResults = emptyList(),
                                isLoading = false,
                                hasSearched = true
                            )
                        }
                        .onFailure { error ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "Search failed",
                                hasSearched = true
                            )
                        }
                }
                SearchMode.BY_INGREDIENT -> {
                    repository.filterMealsByIngredient(query)
                        .onSuccess { meals ->
                            _uiState.value = _uiState.value.copy(
                                previewResults = meals,
                                mealResults = emptyList(),
                                isLoading = false,
                                hasSearched = true
                            )
                        }
                        .onFailure { error ->
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "Search failed",
                                hasSearched = true
                            )
                        }
                }
            }
        }
    }
}
