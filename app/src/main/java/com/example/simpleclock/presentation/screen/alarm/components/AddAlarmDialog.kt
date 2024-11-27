package com.example.simpleclock.presentation.screen.alarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.simpleclock.presentation.screen.timer.components.TimerPicker
import java.time.DayOfWeek

/**
 * Dialog for adding or editing an alarm
 * Features:
 * - Time picker for hour and minute
 * - Day of week selection
 * - Confirm and dismiss buttons
 *
 * @param onDismiss Called when dialog is dismissed
 * @param onConfirm Called with selected time and repeat days when confirmed
 */
@Composable
fun AddAlarmDialog(
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int, repeatDays: Set<DayOfWeek>) -> Unit
) {
    var selectedHour by remember { mutableStateOf(8) }
    var selectedMinute by remember { mutableStateOf(0) }
    var selectedDays by remember { mutableStateOf(emptySet<DayOfWeek>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2B2D42),
        title = {
            Text(
                text = "Add Alarm",
                color = Color.White
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Time picker
                TimerPicker(
                    hours = selectedHour,
                    minutes = selectedMinute,
                    seconds = 0,
                    onTimeSet = { h, m, _ ->
                        selectedHour = h
                        selectedMinute = m
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Repeat days selection
                Text(
                    text = "Repeat",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DayOfWeek.values().forEach { day ->
                        DayToggle(
                            day = day,
                            selected = selectedDays.contains(day),
                            onToggle = {
                                selectedDays = if (selectedDays.contains(day)) {
                                    selectedDays - day
                                } else {
                                    selectedDays + day
                                }
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(selectedHour, selectedMinute, selectedDays)
                }
            ) {
                Text("OK", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.White)
            }
        }
    )
}

@Composable
private fun DayToggle(
    day: DayOfWeek,
    selected: Boolean,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .background(
                color = if (selected) Color(0xFF3A3D50) else Color(0xFF3A3D50).copy(alpha = 0.3f),
                shape = RoundedCornerShape(18.dp)
            )
            .toggleable(
                value = selected,
                onValueChange = { onToggle() }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.name.take(1),
            color = if (selected) Color.White else Color.White.copy(alpha = 0.7f)
        )
    }
} 