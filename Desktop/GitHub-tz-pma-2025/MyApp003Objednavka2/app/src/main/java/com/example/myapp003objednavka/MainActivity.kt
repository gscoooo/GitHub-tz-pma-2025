package com.example.myapp003objednavka

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rgCarModel = findViewById<RadioGroup>(R.id.rgCarModel)
        val cbAudio = findViewById<CheckBox>(R.id.cbAudio)
        val cbHeatedSeats = findViewById<CheckBox>(R.id.cbHeatedSeats)
        val cbTowHitch = findViewById<CheckBox>(R.id.cbTowHitch)
        val btnOrder = findViewById<Button>(R.id.btnOrder)
        val tvSummary = findViewById<TextView>(R.id.tvSummary)
        val ivCar = findViewById<ImageView>(R.id.ivCar)

        rgCarModel.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbSkoda -> ivCar.setImageResource(R.drawable.skoda_octavia)
                R.id.rbVw -> ivCar.setImageResource(R.drawable.vw_passat)
                R.id.rbHyundai -> ivCar.setImageResource(R.drawable.hyundai_i30)
            }
        }

        btnOrder.setOnClickListener {
            val selectedCarId = rgCarModel.checkedRadioButtonId
            if (selectedCarId == -1) {
                tvSummary.text = "Prosím, vyberte model auta."
                return@setOnClickListener
            }

            val selectedCar = findViewById<RadioButton>(selectedCarId).text.toString()

            val extras = mutableListOf<String>()
            if (cbAudio.isChecked) extras.add("Lepší audio")
            if (cbHeatedSeats.isChecked) extras.add("Vyhřívaná sedadla")
            if (cbTowHitch.isChecked) extras.add("Tažné zařízení")

            val summary = StringBuilder("Objednali jste si: $selectedCar")
            if (extras.isNotEmpty()) {
                summary.append("\nS příslušenstvím:")
                extras.forEach { summary.append("\n- $it") }
            }

            tvSummary.text = summary.toString()
        }
    }
}