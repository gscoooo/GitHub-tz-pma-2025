package com.example.myapp005toastsnackbar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toastButton: Button = findViewById(R.id.toast_button)
        toastButton.setOnClickListener {
            // V moderních verzích Androidu (od API 30) již není možné jednoduše změnit ikonu u standardního Toastu.
            // Je nutné vytvořit vlastní layout pro Toast, což je komplikovanější.
            // Zde je ukázka standardního Toastu.
            val toast = Toast.makeText(applicationContext, "Toto je Toast!", Toast.LENGTH_SHORT)
            toast.show()
        }

        val snackbarButton: Button = findViewById(R.id.snackbar_button)
        snackbarButton.setOnClickListener { view ->
            val snackbar = Snackbar.make(view, "Toto je Snackbar!", Snackbar.LENGTH_LONG)
            snackbar.setAction("Zavřít") { 
                // Akce po kliknutí na "Zavřít"
            }
            snackbar.show()
        }
    }
}