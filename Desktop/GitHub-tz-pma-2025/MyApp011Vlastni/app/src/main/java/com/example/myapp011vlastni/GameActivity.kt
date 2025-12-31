package com.example.myapp011vlastni

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private var secretNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Vygenerování nového tajného čísla
        generateNewNumber()

        val guessButton = findViewById<Button>(R.id.guess_button)
        val numberInput = findViewById<EditText>(R.id.number_input)

        guessButton.setOnClickListener {
            val guess = numberInput.text.toString().toIntOrNull()

            if (guess != null) {
                if (guess == secretNumber) {
                    showToast("Správně, získáváš 10% slevu na další pizzu!")
                    generateNewNumber() // Nové kolo
                } else {
                    showToast("Vedle!")
                }
            } else {
                showToast("Zadej platné číslo")
            }
        }
    }

    private fun generateNewNumber() {
        secretNumber = Random.nextInt(1, 11)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
