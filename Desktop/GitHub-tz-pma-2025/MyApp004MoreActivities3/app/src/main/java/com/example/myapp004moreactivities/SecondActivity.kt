package com.example.myapp004moreactivities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView = findViewById<TextView>(R.id.textView_second)
        val message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE)

        textView.text = message
    }
}