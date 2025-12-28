package com.example.myapp008asharedpreferences

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

private val Any.root: Any
private val MainActivity.binding: Any
private var MainActivity.binging: Any

class ActivityMainBinding {
    companion object

}

private fun ActivityMainBinding.Companion.inflate(layoutInflater: LayoutInflater): Any {
    TODO("Not yet implemented")
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Přístup k SharedPreferences

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        //Funkcionalita pro ukádání dat
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString().trim()

            if(age.isNotEmpty() && name.isNotEmpty()) {
                editor.apply {
                    putString("name", name)
                    putString("age", age)
                    apply()
                }

            })


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}