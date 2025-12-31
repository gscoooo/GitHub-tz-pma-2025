package com.example.myapp100semestralka.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapp100semestralka.database.FoodItem
import com.example.myapp100semestralka.databinding.FragmentAddFoodBinding
import java.util.Date

/**
 * Fragment pro obrazovku, kde uživatel přidává novou položku jídla.
 */
class AddFoodFragment : Fragment() {

    // Standardní nastavení ViewBinding pro bezpečný přístup k prvkům v layoutu.
    private var _binding: FragmentAddFoodBinding? = null
    private val binding get() = _binding!!

    // Inicializace ViewModelu pro tento fragment. AddFoodViewModel obsahuje logiku
    // pro vložení nové položky do databáze.
    private val viewModel: AddFoodViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFoodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Nastavení listeneru na tlačítko pro uložení.
        binding.buttonSaveFood.setOnClickListener {
            // Při kliknutí zavoláme naši pomocnou metodu pro uložení.
            saveFoodItem()
        }
    }

    /**
     * Pomocná metoda, která shromáždí data z formuláře, zvaliduje je a uloží.
     */
    private fun saveFoodItem() {
        // 1. Získání dat z editačních polí a RadioButtonů.
        val name = binding.editTextFoodName.text.toString().trim() // .trim() odstraní mezery na začátku a konci
        val caloriesText = binding.editTextFoodCalories.text.toString().trim()
        val selectedRadioButtonId = binding.radioGroupFoodType.checkedRadioButtonId

        // 2. Validace vstupu - kontrola, zda nejsou pole prázdná nebo není vybrán typ jídla.
        if (name.isBlank() || caloriesText.isBlank() || selectedRadioButtonId == -1) {
            // Pokud je něco špatně, zobrazíme uživateli Toast zprávu a dál nepokračujeme.
            Toast.makeText(requireContext(), "Prosím, vyplňte všechna pole", Toast.LENGTH_SHORT).show()
            return // `return` zde ukončí provádění metody saveFoodItem
        }

        // 3. Zpracování a uložení dat
        val type = binding.root.findViewById<RadioButton>(selectedRadioButtonId).text.toString()
        val calories = caloriesText.toInt() // Převod textu na celé číslo

        // Vytvoření instance naší datové třídy FoodItem
        val foodItem = FoodItem(
            name = name,
            calories = calories,
            type = type,
            date = getTodayDate()
        )

        // Zavolání metody ve ViewModelu, která se postará o uložení do databáze.
        viewModel.addFoodItem(foodItem)

        // Zobrazení potvrzovací zprávy.
        Toast.makeText(requireContext(), "Úspěšně přidáno", Toast.LENGTH_SHORT).show()

        // Programový návrat na předchozí obrazovku (MainFragment).
        findNavController().popBackStack()
    }

    /**
     * Pomocná funkce, která vrátí dnešní datum s časem nastaveným na půlnoc.
     * To je důležité, abychom mohli snadno filtrovat jídla pro konkrétní den bez ohledu na čas přidání.
     */
    private fun getTodayDate(): Date {
        val calendar = java.util.Calendar.getInstance()
        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
        calendar.set(java.util.Calendar.MINUTE, 0)
        calendar.set(java.util.Calendar.SECOND, 0)
        calendar.set(java.util.Calendar.MILLISECOND, 0)
        return calendar.time
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Důležitý úklid pro zamezení memory leaků.
    }
}
