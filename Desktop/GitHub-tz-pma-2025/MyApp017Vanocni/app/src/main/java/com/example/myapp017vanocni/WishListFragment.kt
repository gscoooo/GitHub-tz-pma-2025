package com.example.myapp017vanocni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.myapp017vanocni.databinding.FragmentWishListBinding
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Fragment (obrazovka), který zobrazuje seznam uložených vánočních přání.
 * Toto je startovní obrazovka naší aplikace.
 */
class WishListFragment : Fragment() {

    // Proměnná pro View Binding. Umožňuje nám přistupovat k prvkům v layoutu (TextView, Button atd.)
    // bez nutnosti používat starší a méně bezpečný přístup přes `findViewById`.
    // Je "nullable" (může být null), protože view fragmentu existuje jen mezi onCreateView a onDestroyView.
    private var _binding: FragmentWishListBinding? = null
    // Tato druhá proměnná slouží k pohodlnému přístupu k bindingu. "!!" zajišťuje, že pokud
    // se k bindingu pokusíme přistoupit v době, kdy neexistuje (mimo životní cyklus view),
    // aplikace spadne. To je správně, protože by to znamenalo chybu v programování.
    private val binding get() = _binding!!

    /**
     * Tato metoda se volá, když se má vytvořit a "nafouknout" vizuální podoba fragmentu.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // "Nafoukneme" (vytvoříme) layout fragmentu pomocí vygenerované třídy FragmentWishListBinding.
        // Tím propojíme XML layout s naším Kotlin kódem.
        _binding = FragmentWishListBinding.inflate(inflater, container, false)
        // Vrátíme kořenový prvek našeho layoutu (v našem případě ConstraintLayout), který se má zobrazit.
        return binding.root
    }

    /**
     * Tato metoda se volá hned poté, co bylo view úspěšně vytvořeno (onCreateView proběhlo).
     * Zde provádíme veškerou logiku, která s view pracuje (nastavování textů, přidávání listenerů atd.).
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Používáme repeatOnLifecycle pro bezpečný a efektivní sběr dat (např. z DataStore nebo databáze).
        // Tím je zajištěno, že sběr dat se automaticky spustí, když je fragment viditelný pro uživatele (STARTED)
        // a zastaví se, když viditelný není. Tím se šetří systémové prostředky.
        // Zároveň to zaručí, že kód uvnitř bloku `collect` poběží na hlavním UI vlákně, což je nutné pro úpravu UI.
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Začneme poslouchat změny v našem DataStore.
                requireContext().wishListStore.data
                    // Přidáme .catch operátor, který zachytí jakoukoliv chybu při čtení dat.
                    // To je klíčové pro první spuštění, kdy soubor s daty ještě neexistuje.
                    .catch { exception ->
                        if (exception is IOException) {
                            // Pokud je chyba typu IOException (což zahrnuje i případ, kdy je soubor prázdný
                            // nebo poškozený), tak místo pádu aplikace emitujeme (vydáme) výchozí, prázdnou instanci WishList.
                            emit(WishList.getDefaultInstance())
                        } else {
                            // Pokud je to jiná, neočekávaná chyba, necháme aplikaci spadnout, aby se o ní vývojář dozvěděl.
                            throw exception
                        }
                    }
                    // .collect operátor spustí Flow (proud dat) a bude přijímat data.
                    // Tento blok se zavolá pokaždé, když se data v DataStore změní, a také poprvé při spuštění.
                    .collect { wishList ->
                        // Zkontrolujeme, zda je seznam přání prázdný.
                        if (wishList.wishesList.isEmpty()) {
                            // Pokud ano, zobrazíme uživateli informativní text.
                            binding.wishesText.text = "Zatím žádná přání."
                        } else {
                            // Pokud ne, spojíme všechna přání do jednoho textu, kde každé bude na novém řádku s odrážkou.
                            binding.wishesText.text = wishList.wishesList.joinToString("\n- ", "- ")
                        }
                    }
            }
        }

        // Najdeme tlačítko pro přidání přání a nastavíme mu OnClickListener.
        // To je kód, který se provede po kliknutí na tlačítko.
        binding.addWishButton.setOnClickListener {
            // Pomocí NavControlleru provedeme přechod na AddWishFragment.
            // Akce (přechod) s tímto ID je definovaná v našem navigačním grafu (nav_graph.xml).
            findNavController().navigate(R.id.action_wishListFragment_to_addWishFragment)
        }
    }

    /**
     * Tato metoda se volá, když je view fragmentu ničeno (např. při přechodu na jinou obrazovku).
     * Je důležité zde "uklidit" a nastavit _binding na null, abychom předešli únikům paměti.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
