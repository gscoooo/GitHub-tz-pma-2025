package com.example.testznalosti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tato podmínka zajistí, že se fragment vloží pouze při prvním spuštění
        // a ne při každém otočení obrazovky.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment()) // Vložíme náš fragment do kontejneru
                .commit()
        }
    }
}
