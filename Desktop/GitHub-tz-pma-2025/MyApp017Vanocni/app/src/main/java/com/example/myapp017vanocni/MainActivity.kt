package com.example.myapp017vanocni

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.myapp017vanocni.databinding.ActivityMainBinding

/**
 * Hlavní a jediná aktivita v naší aplikaci (Single-Activity Architecture).
 * Jejím hlavním úkolem je hostovat NavHostFragment, který se stará o zobrazování
 * a střídání jednotlivých obrazovek (fragmentů).
 */
class MainActivity : AppCompatActivity() {

    // Deklarace proměnné pro View Binding. 'lateinit' znamená, že ji inicializujeme později.
    // View Binding nám umožňuje bezpečně přistupovat k prvkům v layoutu (tlačítka, texty atd.).
    private lateinit var binding: ActivityMainBinding

    /**
     * Tato metoda se volá při prvním vytvoření aktivity.
     * Je to místo pro základní inicializaci.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vytvoříme instanci třídy ActivityMainBinding, která reprezentuje náš layout activity_main.xml.
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Nastavíme obsah aktivity na kořenový prvek našeho layoutu (v našem případě LinearLayout).
        setContentView(binding.root)

        // Najdeme v našem layoutu Toolbar (horní lištu) a nastavíme ji jako hlavní ActionBar
        // pro tuto aktivitu. To je klíčový krok, aby se v liště mohl zobrazovat titul a šipka zpět.
        setSupportActionBar(binding.toolbar)

        // Najdeme NavHostFragment v našem layoutu. Je to speciální fragment, který slouží
        // jako "okno", ve kterém se budou zobrazovat a střídat ostatní fragmenty (naše obrazovky).
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // Z NavHostFragmentu získáme NavController. Ten je hlavním mozkem celé navigace.
        val navController = navHostFragment.navController

        // Tímto propojíme NavController s horní lištou (ActionBar). Díky tomu se v liště
        // automaticky zobrazí název aktuálního fragmentu (z atributu android:label v nav_graph.xml)
        // a také se objeví šipka zpět, pokud je možné se vrátit na předchozí obrazovku.
        setupActionBarWithNavController(navController)
    }

    /**
     * Tato metoda se zavolá, když uživatel klikne na systémovou šipku "zpět" v horní liště.
     * Bez ní by šipka zpět nic nedělala.
     */
    override fun onSupportNavigateUp(): Boolean {
        // Znovu najdeme NavController.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Řekneme NavControlleru, aby provedl navigaci "zpět" v rámci navigačního grafu.
        // Pokud to z nějakého důvodu nejde (např. jsme na startovní obrazovce), zavolá se
        // výchozí chování (super.onSupportNavigateUp()), které by aplikaci ukončilo.
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
