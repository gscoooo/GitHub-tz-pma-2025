package com.example.myapp014asharedtasklist // Zkontroluj, zda sedí název balíčku!

import android.app.AlertDialog // Import pro vyskakovací okno
import android.os.Bundle
import android.widget.EditText // Import pro zadávací pole v okně
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp014asharedtasklist.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializace databáze
        db = Firebase.firestore

        // Nastavení adapteru s třemi funkcemi (odškrtnutí, smazání, editace)
        adapter = TaskAdapter(
            tasks = emptyList(),
            onChecked = { task -> toggleCompleted(task) },
            onDelete = { task -> deleteTask(task) },
            onEdit = { task -> editTask(task) } // Volání nové funkce editace
        )

        binding.recyclerViewTasks.adapter = adapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)

        // Tlačítko pro přidání nového úkolu
        binding.buttonAdd.setOnClickListener {
            val title = binding.inputTask.text.toString()
            if (title.isNotEmpty()) {
                addTask(title)
                binding.inputTask.text.clear()
            }
        }

        // Spuštění sledování změn v databázi
        listenForTasks()
    }

    // Funkce 1: Přidání úkolu
    private fun addTask(title: String) {
        val task = Task(title = title, completed = false)
        db.collection("tasks").add(task)
    }

    // Funkce 2: Změna stavu (splněno/nesplněno) - OPRAVENO NA ID
    private fun toggleCompleted(task: Task) {
        if (task.id.isNotEmpty()) {
            db.collection("tasks")
                .document(task.id)
                .update("completed", !task.completed)
        }
    }

    // Funkce 3: Smazání úkolu - OPRAVENO NA ID
    private fun deleteTask(task: Task) {
        if (task.id.isNotEmpty()) {
            db.collection("tasks")
                .document(task.id)
                .delete()
        }
    }

    // Funkce 4: Editace úkolu (NOVÉ)
    private fun editTask(task: Task) {
        val editText = EditText(this)
        editText.setText(task.title) // Předvyplníme starý název

        AlertDialog.Builder(this)
            .setTitle("Upravit úkol")
            .setView(editText)
            .setPositiveButton("Uložit") { _, _ ->
                val newTitle = editText.text.toString()
                if (newTitle.isNotEmpty() && task.id.isNotEmpty()) {
                    // Update ve Firestore podle ID
                    db.collection("tasks")
                        .document(task.id)
                        .update("title", newTitle)
                }
            }
            .setNegativeButton("Zrušit", null)
            .show()
    }

    // Sledování databáze v reálném čase
    private fun listenForTasks() {
        db.collection("tasks")
            .addSnapshotListener { snapshots, _ ->
                val taskList = snapshots?.toObjects(Task::class.java) ?: emptyList()
                adapter.submitList(taskList)
            }
    }
}