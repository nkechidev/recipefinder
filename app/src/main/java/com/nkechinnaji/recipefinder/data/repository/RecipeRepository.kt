package com.nkechinnaji.recipefinder.data.repository

import com.nkechinnaji.recipefinder.data.api.RetrofitInstance
import com.nkechinnaji.recipefinder.data.local.FavoriteDao
import com.nkechinnaji.recipefinder.data.local.FavoriteEntity
import com.nkechinnaji.recipefinder.data.model.Category
import com.nkechinnaji.recipefinder.data.model.Ingredient
import com.nkechinnaji.recipefinder.data.model.Meal
import com.nkechinnaji.recipefinder.data.model.MealPreview
import kotlinx.coroutines.flow.Flow

class RecipeRepository(private val favoriteDao: FavoriteDao) {
    
    private val api = RetrofitInstance.api
    
    // API calls
    suspend fun getCategories(): Result<List<Category>> = runCatching {
        api.getCategories().categories
    }
    
    suspend fun getMealsByCategory(category: String): Result<List<MealPreview>> = runCatching {
        api.getMealsByCategory(category).meals ?: emptyList()
    }
    
    suspend fun searchMealsByName(name: String): Result<List<Meal>> = runCatching {
        api.searchMealsByName(name).meals ?: emptyList()
    }
    
    suspend fun filterMealsByIngredient(ingredient: String): Result<List<MealPreview>> = runCatching {
        api.filterMealsByIngredient(ingredient).meals ?: emptyList()
    }
    
    suspend fun getMealById(id: String): Result<Meal?> = runCatching {
        api.getMealById(id).meals?.firstOrNull()
    }
    
    suspend fun getRandomMeal(): Result<Meal?> = runCatching {
        api.getRandomMeal().meals?.firstOrNull()
    }
    
    suspend fun getAllIngredients(): Result<List<Ingredient>> = runCatching {
        api.getAllIngredients().ingredients ?: emptyList()
    }
    
    // Favorites operations
    fun getAllFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()
    
    suspend fun getAllFavoritesList(): List<FavoriteEntity> = favoriteDao.getAllFavoritesList()
    
    fun isFavorite(mealId: String): Flow<Boolean> = favoriteDao.isFavorite(mealId)
    
    suspend fun isFavoriteSync(mealId: String): Boolean = favoriteDao.isFavoriteSync(mealId)
    
    suspend fun addFavorite(meal: Meal) {
        favoriteDao.addFavorite(
            FavoriteEntity(
                id = meal.id,
                name = meal.name,
                thumbnail = meal.thumbnail ?: "",
                category = meal.category,
                area = meal.area
            )
        )
    }
    
    suspend fun addFavoriteFromPreview(preview: MealPreview, category: String? = null) {
        favoriteDao.addFavorite(
            FavoriteEntity(
                id = preview.id,
                name = preview.name,
                thumbnail = preview.thumbnail,
                category = category,
                area = null
            )
        )
    }
    
    suspend fun removeFavorite(mealId: String) {
        favoriteDao.removeFavorite(mealId)
    }
    
    fun getFavoritesCount(): Flow<Int> = favoriteDao.getFavoritesCount()
}
