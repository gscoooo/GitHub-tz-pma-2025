package com.example.myapp010brandomapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var tvAnswer: TextView
    private lateinit var etQuestion: EditText
    private lateinit var btnAsk: Button

    private val answers = listOf(
        "Ano, určitě.",
        "Bezpochyby.",
        "Zcela jistě.",
        "Ano.",
        "Zeptej se později.",
        "Raději ti to neřeknu.",
        "Nevypadá to dobře.",
        "Pochybuji o tom.",
        "Ne.",
        "Zkus se soustředit a zeptej se znovu."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvAnswer = findViewById(R.id.tvAnswer)
        etQuestion = findViewById(R.id.etQuestion)
        btnAsk = findViewById(R.id.btnAsk)

        btnAsk.setOnClickListener {
            if (etQuestion.text.isNotEmpty()) {
                val randomIndex = Random.nextInt(answers.size)
                tvAnswer.text = answers[randomIndex]
                etQuestion.text.clear()
            }
        }
    }
}