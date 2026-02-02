package com.nkechinnaji.recipefinder.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Categories : Screen("categories")
    data object Search : Screen("search")
    data object Random : Screen("random")
    data object Favorites : Screen("favorites")
    data object TonightsPick : Screen("tonights_pick")
    data object MealsByCategory : Screen("meals/{category}") {
        fun createRoute(category: String) = "meals/$category"
    }
    data object MealDetail : Screen("meal/{mealId}") {
        fun createRoute(mealId: String) = "meal/$mealId"
    }
}
