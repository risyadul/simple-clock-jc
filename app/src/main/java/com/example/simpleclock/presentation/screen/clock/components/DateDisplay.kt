package com.example.simpleclock.presentation.screen.clock.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DateDisplay(date: String) {
    Text(
        text = date,
        color = Color.White.copy(alpha = 0.7f),
        fontSize = 24.sp,
        fontWeight = FontWeight.Light
    )
} 