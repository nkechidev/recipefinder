package com.nkechinnaji.recipefinder.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nkechinnaji.recipefinder.navigation.BottomNavItem
import com.nkechinnaji.recipefinder.navigation.IconType

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val items = listOf(
        BottomNavItem.Categories,
        BottomNavItem.Search,
        BottomNavItem.Random,
        BottomNavItem.Favorites,
        BottomNavItem.TonightsPick
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedIndex = items.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(32.dp),
                    clip = false
                )
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.65f))
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 10.dp, vertical = 8.dp)
        ) {
            val itemCount = items.size
            val indicatorWidth = 44.dp
            val slotWidth = maxWidth / itemCount

            val indicatorOffsetX by animateDpAsState(
                targetValue = slotWidth * selectedIndex + (slotWidth - indicatorWidth) / 2,
                animationSpec = spring(
                    dampingRatio = 0.8f,
                    stiffness = 500f
                ),
                label = "indicatorOffset"
            )

            Box(
                modifier = Modifier
                    .offset(x = indicatorOffsetX)
                    .width(indicatorWidth)
                    .height(44.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFFE86A33).copy(alpha = 0.12f))
                    .align(Alignment.CenterStart)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val selected = currentRoute == item.route

                    BottomNavItemView(
                        item = item,
                        selected = selected,
                        enabled = enabled,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = 0.7f,
            stiffness = 400f
        ),
        label = "scale"
    )

    val haptic = LocalHapticFeedback.current

    val iconColor by animateColorAsState(
        targetValue = if (selected) Color(0xFFD35400) else Color(0xFF5F5F5F),
        label = "iconColor"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .clip(RoundedCornerShape(22.dp))
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                    onClick()
                }
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        when (val iconType = item.iconType) {
            is IconType.Vector -> {
                Icon(
                    imageVector = if (selected) iconType.selected else iconType.unselected,
                    contentDescription = item.title,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            is IconType.Custom -> {
                when (iconType.name) {
                    "cutlery" -> CutleryIcon(
                        color = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                    "dice" -> DiceIcon(
                        color = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                    "sparkles" -> SparklesIcon(
                        color = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}