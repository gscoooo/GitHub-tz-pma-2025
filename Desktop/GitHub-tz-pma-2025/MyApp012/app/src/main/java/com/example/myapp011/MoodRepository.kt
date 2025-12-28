package com.example.myapp011

// Dočasné řešení: Jednoduchý objekt (singleton), který drží naše data.
// Oba fragmenty tak mohou přistupovat ke stejnému seznamu.
object MoodRepository {
    val moods = mutableListOf<Mood>()
}
