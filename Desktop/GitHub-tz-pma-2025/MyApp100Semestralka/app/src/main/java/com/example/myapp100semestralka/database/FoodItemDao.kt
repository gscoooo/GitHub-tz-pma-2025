package com.example.myapp100semestralka.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * DAO (Data Access Object) pro entitu FoodItem.
 * Je to rozhraní (interface), které definuje všechny databázové operace (dotazy), které chceme provádět.
 * Nemusíme psát těla těchto metod, Room to udělá za nás na pozadí.
 * Anotace `@Dao` říká Roomu, že toto je DAO rozhraní.
 */
@Dao
interface FoodItemDao {

    /**
     * Vloží novou položku jídla do databáze.
     * Anotace `@Insert` znamená, že Room vygeneruje kód pro standardní SQL INSERT příkaz.
     * Je to `suspend` funkce, protože databázové operace by měly běžet na pozadí v korutinách.
     */
    @Insert
    suspend fun insert(foodItem: FoodItem)

    /**
     * Smaže položku jídla z databáze.
     * Anotace `@Delete` vygeneruje kód pro SQL DELETE příkaz.
     */
    @Delete
    suspend fun delete(foodItem: FoodItem)

    /**
     * Získá všechny položky jídla pro zadané datum, seřazené od nejnovější po nejstarší (podle ID).
     * Anotace `@Query` nám umožňuje psát vlastní SQL dotazy.
     * `:date` je parametr, který se do dotazu dosadí z argumentu metody.
     *
     * Vrací `Flow<List<FoodItem>>`. Flow (proud) je moderní způsob práce s daty, která se mohou měnit v čase.
     * Kdykoliv se v tabulce `food_items` změní data, která odpovídají tomuto dotazu, Flow automaticky
     * "vypustí" nový, aktuální seznam. Naše UI tak může reagovat na změny v databázi v reálném čase.
     */
    @Query("SELECT * FROM food_items WHERE date = :date ORDER BY id DESC")
    fun getFoodItemsForDate(date: Date): Flow<List<FoodItem>>
}
