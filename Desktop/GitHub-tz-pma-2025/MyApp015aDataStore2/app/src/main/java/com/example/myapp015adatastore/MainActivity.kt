package com.example.myapp015adatastore // Zkontroluj svůj package name!

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapp015adatastore.databinding.ActivityMainBinding // Zkontroluj package
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repo: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo = UserPreferencesRepository(this)

        // ---------------------------------------
        // SLEDOVÁNÍ HODNOT (Flow → UI)
        // ---------------------------------------

        // 1) Dark mode
        lifecycleScope.launch {
            repo.darkModeFlow.collectLatest { isDark ->
                // Nastaví přepínač
                binding.switchDarkMode.isChecked = isDark

                // KROK 7: Reakce - změna barev
                binding.textPreview.setTextColor(
                    if (isDark) android.graphics.Color.WHITE else android.graphics.Color.BLACK
                )
                binding.root.setBackgroundColor(
                    if (isDark) android.graphics.Color.DKGRAY else android.graphics.Color.WHITE
                )
            }
        }

        // 2) Username
        lifecycleScope.launch {
            repo.usernameFlow.collectLatest { name ->
                // Nastaví text do inputu
                // Poznámka: Aby kurzor neposkakoval při psaní, obvykle se kontroluje, zda se text liší
                if (binding.editUsername.text.toString() != name) {
                    binding.editUsername.setText(name)
                }

                // KROK 7: Reakce - vypsání jména do náhledu
                binding.textPreview.text = if (name.isEmpty()) "Ukázkový text" else name
            }
        }

        // 3) Font size
        lifecycleScope.launch {
            repo.fontSizeFlow.collectLatest { size ->
                binding.seekFontSize.progress = size
                binding.textFontSizeValue.text = "Velikost: $size"

                // KROK 7: Reakce - změna velikosti písma v náhledu
                binding.textPreview.textSize = size.toFloat()
            }
        }


        // ---------------------------------------
        // REAKCE NA UI A ULOŽENÍ DO DATASTORE
        // ---------------------------------------

        // 1) Přepnutí dark mode
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                repo.setDarkMode(isChecked)
            }
        }

        // 2) Uložení username po kliknutí na tlačítko
        binding.buttonSaveUsername.setOnClickListener {
            val name = binding.editUsername.text.toString()
            lifecycleScope.launch {
                repo.setUsername(name)
            }
        }

        // 3) Změna velikosti fontu (SeekBar)
        binding.seekFontSize.setOnSeekBarChangeListener(
            object : android.widget.SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: android.widget.SeekBar?, value: Int, fromUser: Boolean) {
                    // Jen vizuální změna čísla při posouvání
                    binding.textFontSizeValue.text = "Velikost: $value"
                }

                override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {
                    // Uložení až když uživatel pustí prst
                    seekBar?.let {
                        lifecycleScope.launch {
                            repo.setFontSize(it.progress)
                        }
                    }
                }
            }
        )
    }
}