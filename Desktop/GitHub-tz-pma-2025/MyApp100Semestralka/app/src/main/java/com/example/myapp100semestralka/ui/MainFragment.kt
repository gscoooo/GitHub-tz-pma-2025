package com.example.myapp100semestralka.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp100semestralka.R
import com.example.myapp100semestralka.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Nastavení menu v horní liště
        setupMenu()

        val adapter = FoodAdapter { foodItem ->
            viewModel.deleteFoodItem(foodItem)
            Snackbar.make(binding.root, "Jídlo smazáno", Snackbar.LENGTH_LONG)
                .setAction("Vrátit zpět") { 
                    viewModel.insertFoodItem(foodItem)
                }
                .show()
        }

        binding.recyclerViewFood.adapter = adapter
        binding.recyclerViewFood.layoutManager = LinearLayoutManager(requireContext())

        viewModel.todayFoodItems.observe(viewLifecycleOwner) { foodItems ->
            adapter.submitList(foodItems)
        }

        viewModel.remainingCalories.observe(viewLifecycleOwner) { remaining ->
            // Načteme jméno z ViewModelu
            val userName = viewModel.userName
            val greetingText = if (remaining >= 0) {
                "Ahoj $userName, dnes ti zbývá $remaining kalorií"
            } else {
                "Ahoj $userName, dnes jsi přesáhl/a cíl o ${-remaining} kalorií"
            }
            binding.textViewGreeting.text = greetingText
        }

        binding.fabAddFood.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addFoodFragment)
        }
    }

    /**
     * Metoda pro nastavení menu v horní liště (ActionBar).
     */
    private fun setupMenu() {
        // Získáme MenuHost, což je moderní způsob práce s menu ve fragmentech.
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // "Nafoukneme" naše menu definované v main_menu.xml
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Reakce na kliknutí na položku v menu
                return when (menuItem.itemId) {
                    R.id.action_reset -> {
                        // Pokud uživatel klikne na "Resetovat"
                        viewModel.resetUserData() // Smažeme data v SharedPreferences
                        // A přejdeme zpět na uvítací obrazovku
                        findNavController().navigate(R.id.action_mainFragment_to_welcomeFragment)
                        true // Vracíme true, což znamená, že jsme událost zpracovali
                    }
                    else -> false // Pro všechny ostatní položky vracíme false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
