package com.example.simpleclock.presentation.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.simpleclock.presentation.navigation.BottomNavItem
import com.example.simpleclock.presentation.screen.clock.ClockScreen
import com.example.simpleclock.presentation.screen.clock.ClockViewModel
import com.example.simpleclock.presentation.screen.timer.TimerScreen
import com.example.simpleclock.presentation.screen.timer.TimerViewModel

/**
 * Main container screen for the application that handles navigation between Clock and Timer features
 *
 * Features:
 * - Bottom navigation bar for switching between Clock and Timer screens
 * - Gradient background that's consistent across the app
 * - Maintains selected tab state
 * - Supports initial tab selection (useful for notification navigation)
 *
 * Layout structure:
 * ```
 * [Content Area (Clock/Timer)]
 * [Bottom Navigation Bar    ]
 * ```
 *
 * @param clockViewModel ViewModel for the Clock screen
 * @param timerViewModel ViewModel for the Timer screen
 * @param initialTab The tab to show when first displaying the screen (defaults to Clock)
 */
@Composable
fun MainScreen(
    clockViewModel: ClockViewModel,
    timerViewModel: TimerViewModel,
    initialTab: BottomNavItem = BottomNavItem.Clock
) {
    var selectedItem by remember { mutableStateOf(initialTab) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF1A1B2E),
                contentColor = Color.White
            ) {
                listOf(BottomNavItem.Clock, BottomNavItem.Timer).forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == item,
                        onClick = { selectedItem = item },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.White.copy(alpha = 0.5f),
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.White.copy(alpha = 0.5f),
                            indicatorColor = Color(0xFF3A3D50).copy(alpha = 0.3f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2B2D42),
                            Color(0xFF1A1B2E)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            when (selectedItem) {
                BottomNavItem.Clock -> ClockScreen(clockViewModel)
                BottomNavItem.Timer -> TimerScreen(timerViewModel)
            }
        }
    }
} 