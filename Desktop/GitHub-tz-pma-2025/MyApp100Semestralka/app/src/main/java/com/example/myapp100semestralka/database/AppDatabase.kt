package com.example.myapp100semestralka.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Hlavní třída pro Room databázi.
 * Je to abstraktní třída, která dědí z RoomDatabase.
 * Anotace `@Database` definuje:
 *  - `entities`: Seznam všech datových tříd (tabulek), které budou v této databázi.
 *  - `version`: Verze databáze. Při změně schématu (např. přidání sloupce) se musí zvýšit.
 *  - `exportSchema`: Zda se má schéma exportovat do JSON souboru. Pro jednoduchý projekt to nepotřebujeme.
 */
@Database(entities = [FoodItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // Registrace naší třídy s konvertory typů.
abstract class AppDatabase : RoomDatabase() {

    /**
     * Abstraktní metoda, která vrací naše DAO (Data Access Object).
     * Room se postará o vygenerování konkrétní implementace této metody.
     */
    abstract fun foodItemDao(): FoodItemDao

    /**
     * `companion object` je v Kotlinu obdoba statických metod v Javě.
     * Umožňuje nám mít jednu jedinou instanci databáze pro celou aplikaci (Singleton pattern).
     */
    companion object {
        // `@Volatile` zajišťuje, že instance bude vždy aktuální pro všechna vlákna.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Metoda pro získání instance databáze. Implementuje tzv. Singleton pattern.
         * To zajišťuje, že v celé aplikaci existuje vždy jen JEDNA instance databáze,
         * což je efektivní a bezpečné.
         */
        fun getDatabase(context: Context): AppDatabase {
            // Pokud instance již existuje, vrátíme ji.
            return INSTANCE ?: synchronized(this) {
                // Pokud instance neexistuje, vytvoříme ji uvnitř `synchronized` bloku.
                // Synchronized blok zajistí, že se kód pro vytvoření instance nespustí
                // z více vláken najednou.
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Kontekst celé aplikace
                    AppDatabase::class.java,    // Třída naší databáze
                    "food_database"         // Název souboru databáze v zařízení
                ).build()
                INSTANCE = instance
                // Vrátíme nově vytvořenou instanci.
                instance
            }
        }
    }
}
