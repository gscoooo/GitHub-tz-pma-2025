package com.example.testznalosti

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import kotlin.random.Random

class ProfileFragment : Fragment() {

    // Deklarace prvků z layoutu
    private lateinit var profileImage: ImageView
    private lateinit var userName: TextView
    private lateinit var luckyNumber: TextView
    private lateinit var editProfileButton: Button

    // Moderní způsob, jak čekat na výsledek z jiné aktivity
    // Spustí se, když se vrátíme z EditProfileActivity s výsledkem RESULT_OK
    private val editProfileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Pokud byly data úspěšně uložena, znovu načteme a zobrazíme profil
            loadProfile()
        }
    }

    // Metoda, která se volá při vytváření view pro tento fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // "Nafoukneme" (vytvoříme) náš layout fragment_profile.xml
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    // Metoda, která se volá hned poté, co byl view vytvořen
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializace prvků z layoutu
        profileImage = view.findViewById(R.id.profile_image)
        userName = view.findViewById(R.id.user_name)
        luckyNumber = view.findViewById(R.id.lucky_number)
        editProfileButton = view.findViewById(R.id.edit_profile_button)

        // Načtení a zobrazení profilu při startu
        loadProfile()
        // Vygenerování a zobrazení náhodného čísla
        generateLuckyNumber()

        // Nastavení listeneru pro tlačítko "Upravit profil"
        editProfileButton.setOnClickListener {
            // Vytvoříme Intent pro spuštění EditProfileActivity
            val intent = Intent(activity, EditProfileActivity::class.java)
            // Spustíme aktivitu a čekáme na výsledek
            editProfileLauncher.launch(intent)
        }
    }

    private fun loadProfile() {
        // context je nutný pro přístup k SharedPreferences z fragmentu
        val sharedPrefs = activity?.getSharedPreferences("user_profile", Context.MODE_PRIVATE) ?: return

        // Načteme jméno, pokud není, použijeme výchozí text
        val name = sharedPrefs.getString("name", "Jméno neuvedeno")
        userName.text = name

        // Načteme cestu k obrázku
        val uriString = sharedPrefs.getString("image_uri", null)
        if (uriString != null) {
            // Pokud cesta existuje, převedeme ji na Uri a nastavíme do ImageView
            profileImage.setImageURI(Uri.parse(uriString))
        } else {
            // Pokud ne, nastavíme výchozí ikonu
            profileImage.setImageResource(android.R.drawable.ic_menu_camera)
        }
    }

    private fun generateLuckyNumber() {
        // Vygenerujeme náhodné číslo od 1 do 100
        val number = Random.nextInt(1, 101)
        // Zobrazíme ho v příslušném TextView
        luckyNumber.text = "Tvé šťastné číslo: $number"
    }
}
