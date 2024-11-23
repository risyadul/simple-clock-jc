package com.example.simpleclock.presentation.screen.clock.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Composable that displays the current date
 * Shows date in a semi-transparent white color with light font weight
 *
 * @param date Formatted date string to display
 */
@Composable
fun DateDisplay(date: String) {
    Text(
        text = date,
        color = Color.White.copy(alpha = 0.7f),
        fontSize = 24.sp,
        fontWeight = FontWeight.Light
    )
} 