package com.example.myapp100semestralka.database

import androidx.room.TypeConverter
import java.util.Date

/**
 * Třída obsahující konvertory typů pro Room databázi.
 * Room umí nativně ukládat jen základní datové typy (String, Int, Long, atd.).
 * Pokud chceme do databáze uložit složitější objekt, jako je například `java.util.Date`,
 * musíme Roomu poskytnout způsob, jak tento objekt převést na jednoduchý typ a zpět.
 * Databázový sloupec bude typu `Long` a bude uchovávat tzv. timestamp.
 */
class Converters {

    /**
     * Konvertor z typu Long (timestamp) na Date.
     * Room použije tuto metodu, když čte data z databáze.
     * @param value Hodnota timestampu (počet milisekund od 1.1.1970) z databáze.
     * @return Objekt typu Date.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        // Pokud je hodnota v databázi NULL, vrátíme také null.
        return value?.let { Date(it) }
    }

    /**
     * Konvertor z typu Date na Long (timestamp).
     * Room použije tuto metodu, když zapisuje data do databáze.
     * @param date Objekt typu Date, který chceme uložit.
     * @return Hodnota timestampu (počet milisekund), která se uloží do databáze.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        // Pokud je objekt Date null, uložíme do databáze NULL.
        return date?.time
    }
}
