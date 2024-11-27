package com.example.simpleclock.presentation.screen.alarm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.simpleclock.presentation.screen.alarm.components.AlarmItem
import com.example.simpleclock.presentation.screen.alarm.components.AddAlarmDialog

/**
 * Main screen composable for the alarm feature
 * Features:
 * - List of alarms
 * - Add alarm FAB
 * - Add/Edit alarm dialog
 *
 * @param viewModel ViewModel that manages alarm state and operations
 */
@Composable
fun AlarmScreen(
    viewModel: AlarmViewModel,
    onOpenSettings: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    // Check permission when showing add dialog
    if (showAddDialog && !viewModel.checkAlarmPermission()) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Permission Required") },
            text = { Text("Exact alarm permission is required to set alarms") },
            confirmButton = {
                TextButton(onClick = onOpenSettings) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.alarms.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No alarms set",
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        } else {
            // List of alarms
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(state.alarms) { alarm ->
                    AlarmItem(
                        alarm = alarm,
                        onToggle = { viewModel.toggleAlarm(alarm) },
                        onDelete = { viewModel.deleteAlarm(alarm) }
                    )
                }
            }
        }

        // FAB for adding new alarm
        FloatingActionButton(
            onClick = { showAddDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color(0xFF3A3D50)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add alarm",
                tint = Color.White
            )
        }

        // Add/Edit alarm dialog
        if (showAddDialog) {
            AddAlarmDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { hour, minute, repeatDays ->
                    viewModel.addAlarm(hour, minute, repeatDays)
                    showAddDialog = false
                }
            )
        }
    }
} 