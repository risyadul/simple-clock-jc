package com.example.simpleclock.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    data object Clock : BottomNavItem(
        route = "clock",
        icon = Icons.Rounded.AccessTime,
        label = "Clock"
    )
    
    data object Timer : BottomNavItem(
        route = "timer",
        icon = Icons.Rounded.Timer,
        label = "Timer"
    )
} 