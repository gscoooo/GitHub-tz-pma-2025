package com.example.testznalosti

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    // Deklarace prvků z layoutu
    private lateinit var editName: EditText
    private lateinit var selectImageButton: Button
    private lateinit var saveButton: Button

    // Proměnná pro uložení cesty k vybranému obrázku
    private var imageUri: Uri? = null

    // Moderní způsob, jak získat výsledek z jiné aktivity (v našem případě z galerie)
    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Tato část se provede, když uživatel vybere obrázek
        uri?.let {
            imageUri = it
            // Zde je potřeba zařídit, aby se obrázek trvale zpřístupnil
            contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            Toast.makeText(this, "Obrázek vybrán", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // Inicializace prvků z layoutu
        editName = findViewById(R.id.edit_name)
        selectImageButton = findViewById(R.id.select_image_button)
        saveButton = findViewById(R.id.save_button)

        // Načtení a zobrazení uložených dat při startu
        loadProfile()

        // Nastavení listeneru pro tlačítko "Vybrat obrázek"
        selectImageButton.setOnClickListener {
            // Spustí galerii pro výběr obrázku
            selectImageLauncher.launch("image/*")
        }

        // Nastavení listeneru pro tlačítko "Uložit"
        saveButton.setOnClickListener {
            saveProfile()
        }
    }

    private fun loadProfile() {
        // Získáme přístup k SharedPreferences (soubor pro ukládání jednoduchých dat)
        val sharedPrefs = getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        // Načteme uložené jméno a nastavíme ho do EditTextu
        val name = sharedPrefs.getString("name", "")
        editName.setText(name)
        // Načteme uloženou cestu k obrázku. Pokud existuje, převedeme ji zpět na Uri
        val uriString = sharedPrefs.getString("image_uri", null)
        if (uriString != null) {
            imageUri = Uri.parse(uriString)
        }
    }

    private fun saveProfile() {
        // Získáme přístup k SharedPreferences
        val sharedPrefs = getSharedPreferences("user_profile", Context.MODE_PRIVATE)
        // Získáme editor pro zápis dat
        val editor = sharedPrefs.edit()

        // Uložíme text z EditTextu pod klíčem "name"
        editor.putString("name", editName.text.toString())

        // Pokud byl vybrán nový obrázek, uložíme jeho cestu (převedenou na text)
        imageUri?.let {
            editor.putString("image_uri", it.toString())
        }

        // Potvrdíme uložení změn
        editor.apply()

        // Zobrazíme potvrzovací zprávu (Toast)
        Toast.makeText(this, "Profil byl úspěšně uložen", Toast.LENGTH_SHORT).show()

        // Ukončíme tuto aktivitu a vrátíme se na hlavní obrazovku
        setResult(Activity.RESULT_OK) // Signál pro hlavní obrazovku, že má aktualizovat data
        finish()
    }
}
