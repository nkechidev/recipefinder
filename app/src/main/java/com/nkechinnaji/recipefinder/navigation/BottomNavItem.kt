package com.nkechinnaji.recipefinder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val iconType: IconType
) {
    data object Categories : BottomNavItem(
        route = Screen.Categories.route,
        title = "Browse",
        iconType = IconType.Custom("cutlery")
    )
    
    data object Search : BottomNavItem(
        route = Screen.Search.route,
        title = "Search",
        iconType = IconType.Vector(Icons.Outlined.Search, Icons.Filled.Search)
    )
    
    data object Random : BottomNavItem(
        route = Screen.Random.route,
        title = "Random",
        iconType = IconType.Custom("dice")
    )
    
    data object Favorites : BottomNavItem(
        route = Screen.Favorites.route,
        title = "Favorites",
        iconType = IconType.Vector(Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite)
    )
    
    data object TonightsPick : BottomNavItem(
        route = Screen.TonightsPick.route,
        title = "Tonight",
        iconType = IconType.Custom("sparkles")
    )
}

sealed class IconType {
    data class Vector(val unselected: ImageVector, val selected: ImageVector) : IconType()
    data class Custom(val name: String) : IconType()
}
