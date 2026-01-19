package com.nkechinnaji.recipefinder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nkechinnaji.recipefinder.data.local.RecipeDatabase
import com.nkechinnaji.recipefinder.data.repository.RecipeRepository
import com.nkechinnaji.recipefinder.ui.screens.categories.CategoriesScreen
import com.nkechinnaji.recipefinder.ui.screens.categories.CategoriesViewModel
import com.nkechinnaji.recipefinder.ui.screens.favorites.FavoritesScreen
import com.nkechinnaji.recipefinder.ui.screens.favorites.FavoritesViewModel
import com.nkechinnaji.recipefinder.ui.screens.mealdetail.MealDetailScreen
import com.nkechinnaji.recipefinder.ui.screens.mealdetail.MealDetailViewModel
import com.nkechinnaji.recipefinder.ui.screens.mealsbycategory.MealsByCategoryScreen
import com.nkechinnaji.recipefinder.ui.screens.mealsbycategory.MealsByCategoryViewModel
import com.nkechinnaji.recipefinder.ui.screens.random.RandomScreen
import com.nkechinnaji.recipefinder.ui.screens.random.RandomViewModel
import com.nkechinnaji.recipefinder.ui.screens.search.SearchScreen
import com.nkechinnaji.recipefinder.ui.screens.search.SearchViewModel
import com.nkechinnaji.recipefinder.ui.screens.tonightspick.TonightsPickScreen
import com.nkechinnaji.recipefinder.ui.screens.tonightspick.TonightsPickViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val database = remember { RecipeDatabase.getDatabase(context) }
    val repository = remember { RecipeRepository(database.favoriteDao()) }
    
    // ViewModels
    val categoriesViewModel = remember { CategoriesViewModel(repository) }
    val searchViewModel = remember { SearchViewModel(repository) }
    val randomViewModel = remember { RandomViewModel(repository) }
    val favoritesViewModel = remember { FavoritesViewModel(repository) }
    val tonightsPickViewModel = remember { TonightsPickViewModel(repository) }
    val mealsByCategoryViewModel = remember { MealsByCategoryViewModel(repository) }
    val mealDetailViewModel = remember { MealDetailViewModel(repository) }
    
    NavHost(
        navController = navController,
        startDestination = Screen.Categories.route,
        modifier = modifier
    ) {
        composable(Screen.Categories.route) {
            CategoriesScreen(
                viewModel = categoriesViewModel,
                onCategoryClick = { category ->
                    navController.navigate(Screen.MealsByCategory.createRoute(category))
                }
            )
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                viewModel = searchViewModel,
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                }
            )
        }
        
        composable(Screen.Random.route) {
            RandomScreen(
                viewModel = randomViewModel,
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                }
            )
        }
        
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = favoritesViewModel,
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                }
            )
        }
        
        composable(Screen.TonightsPick.route) {
            TonightsPickScreen(
                viewModel = tonightsPickViewModel,
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                }
            )
        }
        
        composable(
            route = Screen.MealsByCategory.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            MealsByCategoryScreen(
                category = category,
                viewModel = mealsByCategoryViewModel,
                onMealClick = { mealId ->
                    navController.navigate(Screen.MealDetail.createRoute(mealId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.MealDetail.route,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            MealDetailScreen(
                mealId = mealId,
                viewModel = mealDetailViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
