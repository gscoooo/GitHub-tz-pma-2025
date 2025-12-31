package com.example.myapp017vanocni

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

/**
 * Jediná centrální definice pro přístup k DataStore v celé aplikaci.
 * Toto je doporučený postup, jak se vyhnout konfliktům a zajistit, že vždy pracujeme
 * se stejnou instancí DataStore. Vytváří se zde "singleton" (jedináček) pro celý kontext aplikace.
 *
 * 'by dataStore' je speciální delegát, který za nás spravuje vytváření a přístup k souboru.
 */
val Context.wishListStore: DataStore<WishList> by dataStore(
    // Název souboru na disku zařízení, kam se budou data ukládat.
    // Přípona .pb značí "Protocol Buffers", což je formát, který používáme.
    fileName = "wish_list.pb",

    // Serializer je objekt, který ví, jak převést náš Kotlin objekt (WishList) na bajty
    // pro uložení do souboru a naopak.
    serializer = WishListSerializer
)
