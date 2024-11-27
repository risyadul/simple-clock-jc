package com.example.simpleclock.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Clock : BottomNavItem("clock", "Clock", Icons.Default.AccessTime)
    object Timer : BottomNavItem("timer", "Timer", Icons.Default.Timer)
    object Alarm : BottomNavItem("alarm", "Alarm", Icons.Default.Alarm)
} 