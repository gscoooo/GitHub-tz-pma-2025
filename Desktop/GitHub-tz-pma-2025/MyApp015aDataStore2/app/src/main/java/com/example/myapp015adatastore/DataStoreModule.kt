package com.example.myapp015adatastore // Zkontroluj svůj package name!

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// Toto vytvoří rozšíření pro Context, abychom mohli snadno přistupovat k datům
val Context.settingsDataStore by preferencesDataStore("settings")