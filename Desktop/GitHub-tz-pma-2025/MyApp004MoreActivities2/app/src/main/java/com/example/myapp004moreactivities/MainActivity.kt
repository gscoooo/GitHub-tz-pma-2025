package com.example.myapp004moreactivities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnSecondAct = findViewById<Button>(R.id.SecondAct)
        val eNickname = findViewById<EditText>(R.id.eNickname)

        btnSecondAct.setOnClickListener {
            val nickname = eNickname.text.toString()
            val intent = Intent(this, SecondActivity::class.java)

        }

        }
    }

