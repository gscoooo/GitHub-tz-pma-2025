package com.example.myapp016anavigationdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Inicializace NavControlleru
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHost.navController

        // 2. Najdeme Toolbar a DrawerLayout
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout) // ID jsem přidal v kroku 5 do XML

        // Nastavíme Toolbar jako Action Bar
        setSupportActionBar(toolbar)

        // 3. Propojení Toolbaru s Navigací a Drawerem (aby fungovalo hamburger tlačítko)
        // Definujeme, které fragmenty jsou "hlavní" (kde se nezobrazuje šipka zpět, ale hamburger)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.profileFragment, R.id.settingsFragment, R.id.aboutFragment),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // 4. Propojení Bottom Navigation
        findViewById<BottomNavigationView>(R.id.bottomNav)
            .setupWithNavController(navController)

        // 5. Propojení Drawer Navigation
        findViewById<NavigationView>(R.id.drawerNav)
            .setupWithNavController(navController)
    }
}