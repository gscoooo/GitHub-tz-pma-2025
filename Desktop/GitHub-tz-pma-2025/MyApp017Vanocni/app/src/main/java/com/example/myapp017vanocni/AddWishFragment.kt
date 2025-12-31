package com.example.myapp017vanocni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapp017vanocni.databinding.FragmentAddWishBinding
import kotlinx.coroutines.launch

/**
 * Fragment (obrazovka) pro přidání nového vánočního přání do seznamu.
 */
class AddWishFragment : Fragment() {

    // Stejný princip View Binding jako ve WishListFragment.
    // Proměnná pro bezpečný přístup k prvkům v layoutu.
    private var _binding: FragmentAddWishBinding? = null
    private val binding get() = _binding!!

    /**
     * Metoda pro vytvoření a "nafouknutí" vizuální podoby fragmentu.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Propojíme XML layout s Kotlin kódem.
        _binding = FragmentAddWishBinding.inflate(inflater, container, false)
        // Vrátíme kořenový prvek layoutu, který se má zobrazit.
        return binding.root
    }

    /**
     * Metoda, která se volá hned po vytvoření view.
     * Zde umisťujeme logiku, která s view pracuje.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Najdeme tlačítko pro uložení a nastavíme mu OnClickListener.
        binding.saveButton.setOnClickListener {
            // Získáme text zadaný uživatelem z textového pole (EditText).
            val wishText = binding.wishInput.text.toString()

            // Zkontrolujeme, zda uživatel něco napsal (není to jen prázdný text nebo mezery).
            if (wishText.isNotBlank()) {
                // Spustíme coroutine (asynchronní operaci), protože zápis do souboru (DataStore)
                // musí proběhnout na pozadí a nesmí blokovat hlavní UI vlákno.
                lifecycleScope.launch {
                    // Zavoláme metodu updateData na naší instanci DataStore.
                    // Toto je bezpečný způsob, jak aktualizovat uložená data.
                    requireContext().wishListStore.updateData {
                        // "it" zde představuje aktuální stav uloženého objektu WishList.
                        // .toBuilder() vytvoří modifikovatelnou kopii tohoto objektu.
                        // .addWishes(wishText) přidá náš nový textový řetězec do seznamu přání v tomto objektu.
                        // .build() vytvoří nový, neměnný objekt WishList s přidaným přáním, který se finálně uloží.
                        it.toBuilder().addWishes(wishText).build()
                    }
                    // Po úspěšném uložení se pomocí NavControlleru vrátíme zpět na předchozí obrazovku (WishListFragment).
                    findNavController().popBackStack()
                }
            }
        }
    }

    /**
     * Metoda pro úklid, když je view zničeno.
     * Nastavením _binding na null předejdeme únikům paměti.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
