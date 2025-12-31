package com.example.myapp100semestralka.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Datová třída (data class), která reprezentuje jednu položku jídla.
 * Toto je "model" našich dat.
 * Anotace `@Entity` říká Roomu, že z této třídy má vytvořit tabulku v databázi.
 * @param tableName Nepovinný parametr, kterým můžeme určit název tabulky. Pokud ho neuvedeme,
 *                použije se název třídy, tedy "FoodItem".
 */
@Entity(tableName = "food_items")
data class FoodItem(
    /**
     * Anotace `@PrimaryKey` označuje tento sloupec jako primární klíč tabulky.
     * Primární klíč je unikátní identifikátor pro každý záznam (řádek).
     * `autoGenerate = true` zajistí, že Room bude automaticky generovat nové, unikátní ID
     * pro každou nově vloženou položku.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Výchozí hodnota 0 je důležitá pro autogenerování.

    // Následující vlastnosti třídy budou převedeny na sloupce v databázové tabulce.
    val name: String,
    val calories: Int,
    val type: String,
    val date: Date
)
