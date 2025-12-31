package com.example.myapp014asharedtasklist

import com.google.firebase.firestore.DocumentId // Nutný import!

data class Task(
    @DocumentId
    val id: String = "", // Tady se uloží ID dokumentu
    val title: String = "",
    val completed: Boolean = false
)