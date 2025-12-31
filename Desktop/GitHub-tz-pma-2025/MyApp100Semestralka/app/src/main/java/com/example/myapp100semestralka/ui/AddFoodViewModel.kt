package com.example.myapp100semestralka.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp100semestralka.database.AppDatabase
import com.example.myapp100semestralka.database.FoodItem
import com.example.myapp100semestralka.database.FoodItemDao
import kotlinx.coroutines.launch

/**
 * ViewModel pro AddFoodFragment.
 * Jeho jediným úkolem je poskytnout metodu pro přidání nové položky jídla do databáze.
 */
class AddFoodViewModel(application: Application) : AndroidViewModel(application) {

    // Reference na DAO pro přístup k databázi.
    private val foodItemDao: FoodItemDao

    init {
        // Inicializace databáze a DAO.
        val db = AppDatabase.getDatabase(application)
        foodItemDao = db.foodItemDao()
    }

    /**
     * Veřejná metoda, kterou volá fragment, aby uložil novou položku jídla.
     * @param foodItem Objekt jídla, který má být vložen do databáze.
     */
    fun addFoodItem(foodItem: FoodItem) {
        // Spustíme korutinu, aby databázová operace neblokovala hlavní vlákno.
        viewModelScope.launch {
            foodItemDao.insert(foodItem)
        }
    }
}
