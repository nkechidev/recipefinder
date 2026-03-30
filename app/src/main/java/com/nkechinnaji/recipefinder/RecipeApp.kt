package com.nkechinnaji.recipefinder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.draw.alpha
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nkechinnaji.recipefinder.navigation.NavGraph
import com.nkechinnaji.recipefinder.navigation.Screen
import com.nkechinnaji.recipefinder.ui.components.BottomNavigationBar
import com.nkechinnaji.recipefinder.ui.components.RecipeTopBar
import com.nkechinnaji.recipefinder.ui.theme.BorderLight

@Composable
fun RecipeApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Hide bottom bar and top bar on welcome screen and detail screens
    val showBars = currentRoute in listOf(
        Screen.Categories.route,
        Screen.Search.route,
        Screen.Random.route,
        Screen.Favorites.route,
        Screen.TonightsPick.route
    )
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(modifier = Modifier.alpha(if (showBars) 1f else 0f)) {
                RecipeTopBar()
                HorizontalDivider(
                    thickness = 1.dp,
                    color = BorderLight
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                enabled = showBars,
                modifier = Modifier.alpha(if (showBars) 1f else 0f)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                // Only inset for top/bottom bars on tab screens. Applying Scaffold padding while
                // Welcome is still visible (during NavHost transitions) shrinks the scroll area
                // and the bottom button can clip away before the image does.
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            NavGraph(
                navController = navController
            )
        }
    }
}
