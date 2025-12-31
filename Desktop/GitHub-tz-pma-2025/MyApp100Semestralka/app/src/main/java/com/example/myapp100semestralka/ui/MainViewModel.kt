package com.example.myapp100semestralka.ui

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp100semestralka.database.AppDatabase
import com.example.myapp100semestralka.database.FoodItem
import com.example.myapp100semestralka.database.FoodItemDao
import kotlinx.coroutines.launch
import java.util.Date

/**
 * ViewModel pro MainFragment.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val foodItemDao: FoodItemDao
    // Přidáme referenci na SharedPreferences.
    private val sharedPreferences: SharedPreferences

    val todayFoodItems: LiveData<List<FoodItem>>

    private val _remainingCalories = MutableLiveData<Int>()
    val remainingCalories: LiveData<Int> = _remainingCalories

    // Veřejná vlastnost pro jméno uživatele, kterou si může přečíst fragment.
    val userName: String

    init {
        val db = AppDatabase.getDatabase(application)
        foodItemDao = db.foodItemDao()
        // Inicializujeme SharedPreferences.
        sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Načteme jméno a cíl z SharedPreferences. Pokud tam nejsou, použijí se výchozí hodnoty.
        userName = sharedPreferences.getString("user_name", "Uživatel") ?: "Uživatel"
        val caloriesTarget = sharedPreferences.getInt("calories_target", 2000)

        todayFoodItems = foodItemDao.getFoodItemsForDate(getTodayDate()).asLiveData()

        todayFoodItems.observeForever {
            val totalCalories = it.sumOf { food -> food.calories }
            _remainingCalories.value = caloriesTarget - totalCalories
        }
    }

    fun deleteFoodItem(foodItem: FoodItem) {
        viewModelScope.launch {
            foodItemDao.delete(foodItem)
        }
    }

    fun insertFoodItem(foodItem: FoodItem) {
        viewModelScope.launch {
            foodItemDao.insert(foodItem)
        }
    }

    /**
     * Metoda pro smazání všech uživatelských dat z SharedPreferences.
     */
    fun resetUserData() {
        with(sharedPreferences.edit()) {
            clear() // Smaže všechny záznamy v "user_prefs"
            apply()
        }
    }

    private fun getTodayDate(): Date {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.time
    }
}
