plugins {
    // Základní plugin pro Android aplikační modul.
    alias(libs.plugins.android.application)
    // Základní plugin pro podporu jazyka Kotlin v Androidu.
    alias(libs.plugins.kotlin.android)
    // Plugin pro zpracování anotací v Kotlinu (Annotation Processing Tool).
    // Je naprosto klíčový pro Room, protože generuje kód na pozadí na základě našich anotací (@Dao, @Database, atd.).
    id("kotlin-kapt")
}

android {
    namespace = "com.example.myapp100semestralka"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapp100semestralka"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    // Tato sekce zapíná/vypíná různé funkce v Android Gradle Pluginu.
    buildFeatures {
        // Aktivuje ViewBinding. To nám umožňuje přistupovat k XML layoutům
        // typově bezpečným způsobem, bez nutnosti používat `findViewById`.
        viewBinding = true
    }
}

// Sekce `dependencies` definuje všechny externí knihovny, které naše aplikace potřebuje.
dependencies {

    // --- ZÁKLADNÍ KNIHOVNY ANDROIDX ---
    implementation(libs.androidx.core.ktx) // Kotlin rozšíření pro základní Android framework.
    implementation(libs.androidx.appcompat) // Knihovna pro zpětnou kompatibilitu UI prvků (např. ActionBar).
    implementation(libs.material) // Implementace Material Design komponent (tlačítka, karty, atd.).
    implementation(libs.androidx.activity) // Správa životního cyklu aktivit.
    implementation(libs.androidx.constraintlayout) // Knihovna pro tvorbu flexibilních layoutů.

    // --- NAVIGATION COMPONENT ---
    // Knihovny pro moderní navigaci v aplikaci pomocí fragmentů a navigačního grafu.
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // --- ROOM DATABÁZE ---
    // Knihovny pro lokální databázi.
    implementation("androidx.room:room-runtime:2.6.1") // Hlavní běhová komponenta Roomu.
    implementation("androidx.room:room-ktx:2.6.1") // Kotlin rozšíření (KTX) pro lepší práci s Roomem (podpora pro Coroutines a Flow).
    kapt("androidx.room:room-compiler:2.6.1") // Kompilátor, který na základě našich anotací generuje potřebný kód.

    // --- KOTLIN COROUTINES ---
    // Knihovna pro asynchronní programování. Používáme ji, aby operace s databází neblokovaly hlavní (UI) vlákno.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // --- LIFECYCLE KOMPONENTY (PRO VIEWMODEL) ---
    // Knihovny pro správu životního cyklu a ViewModel, který přežívá změny konfigurace.
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1") // Poskytuje třídu ViewModel a delegáta `by viewModels()`.
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1") // Umožňuje komponentám jako LiveData sledovat životní cyklus (LifecycleOwner).

    // --- TESTOVACÍ KNIHOVNY ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
