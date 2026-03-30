package com.nkechinnaji.recipefinder.data.api

import com.nkechinnaji.recipefinder.BuildConfig
import com.nkechinnaji.recipefinder.data.model.CategoriesResponse
import com.nkechinnaji.recipefinder.data.model.IngredientsResponse
import com.nkechinnaji.recipefinder.data.model.MealPreviewResponse
import com.nkechinnaji.recipefinder.data.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApiService {

    @GET("{apiKey}/categories.php")
    suspend fun getCategories(@Path("apiKey") apiKey: String = BuildConfig.MEAL_DB_API_KEY): CategoriesResponse

    @GET("{apiKey}/filter.php")
    suspend fun getMealsByCategory(
        @Path("apiKey") apiKey: String = BuildConfig.MEAL_DB_API_KEY,
        @Query("c") category: String): MealPreviewResponse

    @GET("{apiKey}/search.php")
    suspend fun searchMealsByName(
        @Path("apiKey") apiKey: String = BuildConfig.MEAL_DB_API_KEY,
        @Query("s") name: String): MealsResponse

    @GET("{apiKey}/filter.php")
    suspend fun filterMealsByIngredient(
        @Path("apiKey") apiKey: String = BuildConfig.MEAL_DB_API_KEY,
        @Query("i") ingredient: String): MealPreviewResponse

    @GET("{apiKey}/lookup.php")
    suspend fun getMealById(
        @Path("apiKey") apiKey: String = BuildConfig.MEAL_DB_API_KEY,
        @Query("i") id: String): MealsResponse

    @GET("{apiKey}/random.php")
    suspend fun getRandomMeal(@Path("apiKey") apiKey: String = BuildConfig.MEAL_DB_API_KEY): MealsResponse

    @GET("{apiKey}/list.php?i=list")
    suspend fun getAllIngredients(@Path("apiKey") apiKey: String = BuildConfig.MEAL_DB_API_KEY): IngredientsResponse
}
