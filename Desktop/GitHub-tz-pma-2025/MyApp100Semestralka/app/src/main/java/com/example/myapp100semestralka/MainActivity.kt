package com.example.myapp100semestralka

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapp100semestralka.databinding.ActivityMainBinding

/**
 * MainActivity je hlavní a jediná aktivita v naší aplikaci.
 * Slouží jako hlavní kontejner pro všechny naše obrazovky (fragmenty).
 * V moderním vývoji pro Android se preferuje přístup s jednou aktivitou (Single-Activity Architecture).
 */
class MainActivity : AppCompatActivity() {

    // Proměnná pro ViewBinding. Umožňuje nám přistupovat k prvkům z XML layoutu
    // bez nutnosti používat starší a méně bezpečné `findViewById`.
    // `lateinit` znamená, že slibujeme, že tuto proměnnou inicializujeme později (v onCreate).
    private lateinit var binding: ActivityMainBinding

    // NavController je mozkem Navigation Component. Stará se o výměnu fragmentů
    // v kontejneru a o správu "back stacku" (historie obrazovek).
    private lateinit var navController: NavController

    /**
     * Metoda `onCreate` je volána, když je aktivita poprvé vytvořena.
     * Je to místo, kde provádíme veškerou úvodní inicializaci.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Zde "nafoukneme" (inflate) náš XML layout (activity_main.xml) do objektu `binding`.
        binding = ActivityMainBinding.inflate(layoutInflater)
        // A nastavíme tento layout jako hlavní obsah naší aktivity.
        setContentView(binding.root)

        // --- NASTAVENÍ NAVIGACE A HORNÍ LIŠTY (TOOLBAR) ---

        // 1. Nastavíme náš Toolbar z layoutu (s ID 'toolbar') jako oficiální ActionBar pro tuto aktivitu.
        //    Tento krok je klíčový, aby systém věděl, kterou lištu má používat.
        setSupportActionBar(binding.toolbar)

        // 2. Najdeme NavHostFragment. To je speciální fragment, který slouží jako kontejner
        //    a zobrazuje všechny ostatní fragmenty (mainFragment, addFoodFragment atd.).
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Z NavHostFragmentu získáme jeho NavController.
        navController = navHostFragment.navController

        // 3. Propojíme NavController s ActionBarem, který jsme nastavili v kroku 1.
        //    Díky tomu se bude v liště automaticky zobrazovat název aktuálního fragmentu
        //    a šipka zpět, pokud je to možné.
        setupActionBarWithNavController(navController)
    }

    /**
     * Tato metoda je volána, když uživatel klikne na šipku zpět v horní liště (ActionBar).
     * Předáme tuto událost NavControlleru, který se postará o správný návrat na předchozí obrazovku.
     */
    override fun onSupportNavigateUp(): Boolean {
        // Pokud NavController dokáže přejít zpět, udělá to. Jinak se použije výchozí chování.
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
