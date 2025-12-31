package com.example.myapp100semestralka.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapp100semestralka.R
import com.example.myapp100semestralka.databinding.FragmentWelcomeBinding

/**
 * Fragment pro úvodní obrazovku.
 * Slouží jako "brána" do aplikace. Zkontroluje, zda už uživatel zadal svá data.
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val userName = sharedPrefs.getString("user_name", null)

        if (userName != null) {
            findNavController().navigate(R.id.action_welcomeFragment_to_mainFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val caloriesTarget = binding.editTextCaloriesTarget.text.toString().trim()

            if (name.isBlank() || caloriesTarget.isBlank()) {
                Toast.makeText(requireContext(), "Prosím, vyplňte všechna pole", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPrefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putString("user_name", name)
                putInt("calories_target", caloriesTarget.toInt())
                apply()
            }

            findNavController().navigate(R.id.action_welcomeFragment_to_mainFragment)
        }
    }

    /**
     * Když se fragment zobrazí (vrátí se do popředí), skryjeme horní lištu (ActionBar),
     * protože na této obrazovce není potřeba a má vlastní velký nadpis.
     */
    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    /**
     * Když z fragmentu odcházíme (přecházíme na jiný), znovu zobrazíme horní lištu,
     * aby byla viditelná na dalších obrazovkách (např. na MainFragment).
     */
    override fun onPause() {
        super.onPause()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
