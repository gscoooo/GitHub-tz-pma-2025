package com.example.myapp011vlastni

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp011vlastni.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // Deklarace proměnné pro View Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializace View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Nastavení posluchače pro RadioGroup pro změnu obrázku
        binding.pizzaGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
             when (selectedRadioButton.id) {
                R.id.salami -> binding.pizzaImage.setImageResource(R.drawable.salami)
                R.id.hawai -> binding.pizzaImage.setImageResource(R.drawable.hawai)
                R.id.margherita -> binding.pizzaImage.setImageResource(R.drawable.margherita)
            }
        }

        // Nastavení posluchače pro tlačítko "Objednat"
        binding.orderButton.setOnClickListener { 
            // Získání vybrané pizzy
            val selectedPizzaId = binding.pizzaGroup.checkedRadioButtonId
            if (selectedPizzaId == -1) {
                showToast("Prosím, vyberte si pizzu.")
                return@setOnClickListener
            }
            val pizza = findViewById<RadioButton>(selectedPizzaId).text.toString()

            // Získání extra přísad
            val extras = mutableListOf<String>()
            if (binding.cheeseCheckbox.isChecked) {
                extras.add("extra sýr")
            }
            if (binding.olivesCheckbox.isChecked) {
                extras.add("olivy")
            }

            // Sestavení souhrnu objednávky
            val summary = "Objednávka: $pizza, Extra: ${extras.joinToString()}"

            // Zobrazení souhrnu v TextView
            binding.summaryText.text = summary

            // Zobrazení Toastu a Snackbaru
            showToast("Objednávka přijata!")
            //showSnackbar("Děkujeme za objednávku!")
        }

        // Tlačítko pro přechod na hru
        binding.gameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    // Funkce pro zobrazení Toastu
    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

    // Funkce pro zobrazení Snackbaru
    /*private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction("Zavřít") { 
                // Akce po kliknutí na tlačítko ve Snackbaru
            }
         .show()
    */
    }

