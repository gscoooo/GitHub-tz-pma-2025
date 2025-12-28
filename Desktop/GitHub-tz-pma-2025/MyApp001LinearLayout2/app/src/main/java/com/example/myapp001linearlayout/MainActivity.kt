package com.example.myapp001linearlayout

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etMunicipality = findViewById<EditText>(R.id.etMunicipality)
        val etAge = findViewById<EditText>(R.id.etAge)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnSubmit.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val municipality = etMunicipality.text.toString()
            val age = etAge.text.toString()

            val resultText = "Jméno: $firstName\nPříjmení: $lastName\nObec: $municipality\nVěk: $age"
            tvResult.text = resultText
        }

        btnClear.setOnClickListener {
            etFirstName.text.clear()
            etLastName.text.clear()
            etMunicipality.text.clear()
            etAge.text.clear()
            tvResult.text = "text"
        }
    }
}