package com.nkechinnaji.recipefinder.data.api

import com.nkechinnaji.recipefinder.data.model.CategoriesResponse
import com.nkechinnaji.recipefinder.data.model.IngredientsResponse
import com.nkechinnaji.recipefinder.data.model.MealPreviewResponse
import com.nkechinnaji.recipefinder.data.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse
    
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealPreviewResponse
    
    @GET("search.php")
    suspend fun searchMealsByName(@Query("s") name: String): MealsResponse
    
    @GET("filter.php")
    suspend fun filterMealsByIngredient(@Query("i") ingredient: String): MealPreviewResponse
    
    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealsResponse
    
    @GET("random.php")
    suspend fun getRandomMeal(): MealsResponse
    
    @GET("list.php?i=list")
    suspend fun getAllIngredients(): IngredientsResponse
}
