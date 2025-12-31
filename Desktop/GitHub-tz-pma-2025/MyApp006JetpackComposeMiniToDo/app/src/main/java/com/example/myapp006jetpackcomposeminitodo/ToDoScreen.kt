package com.example.myapp006jetpackcomposeminitodo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TodoItem(
    val title: String,
    var isDone: Boolean = false
)

@Composable
fun ToDoScreen(modifier: Modifier = Modifier) {
    // State for the text field
    var text by remember { mutableStateOf("") }
    // State for the list of tasks
    val tasks = remember { mutableStateListOf<TodoItem>() }
    // State for managing the confirmation dialog
    var showDeleteDialog by remember { mutableStateOf<TodoItem?>(null) }
    var showDeleteCompletedDialog by remember { mutableStateOf(false) }

    // --- Confirmation Dialog for Deleting a Single Task ---
    showDeleteDialog?.let { taskToDelete ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete the task \"${taskToDelete.title}\"?") },
            confirmButton = {
                Button(
                    onClick = {
                        tasks.remove(taskToDelete)
                        showDeleteDialog = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // --- Confirmation Dialog for Deleting All Completed Tasks ---
    if (showDeleteCompletedDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteCompletedDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete all completed tasks?") },
            confirmButton = {
                Button(
                    onClick = {
                        tasks.removeAll { it.isDone }
                        showDeleteCompletedDialog = false
                    }
                ) {
                    Text("Delete All")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteCompletedDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // ----- Input Row with TextField + Add Button -----
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("New task") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        tasks.add(0, TodoItem(title = text)) // Add new items to the top
                        text = ""
                    }
                },
                enabled = text.isNotBlank()
            ) {
                Text("+")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ----- Status Display and "Delete Completed" Button -----
        val completedCount = tasks.count { it.isDone }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total: ${tasks.size} | Completed: $completedCount",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (completedCount > 0) {
                TextButton(onClick = { showDeleteCompletedDialog = true }) {
                    Text("Delete Completed")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ----- List of Tasks -----
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(tasks, key = { _, task -> task.title }) { index, task ->
                val cardColor = if (task.isDone) Color(0xFFC8E6C9) else MaterialTheme.colorScheme.surfaceVariant

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            tasks[index] = task.copy(isDone = !task.isDone)
                        },
                    colors = CardDefaults.cardColors(containerColor = cardColor)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = task.title,
                            textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                            color = if (task.isDone) Color.DarkGray else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            showDeleteDialog = task // Show confirmation dialog
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete task"
                            )
                        }
                    }
                }
            }
        }
    }
}
