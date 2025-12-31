package com.example.myapp100semestralka.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp100semestralka.database.FoodItem
import com.example.myapp100semestralka.databinding.ItemFoodBinding

/**
 * Adapter pro RecyclerView na hlavní obrazovce.
 * Adapter je zodpovědný za vytvoření a naplnění pohledů (views) pro každou položku v seznamu.
 * Dědíme z `ListAdapter`, což je moderní a efektivní typ adaptéru.
 * @param onDeleteClicked Lambda funkce, která se zavolá, když uživatel klikne na ikonu pro smazání.
 */
class FoodAdapter(private val onDeleteClicked: (FoodItem) -> Unit) : ListAdapter<FoodItem, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {

    /**
     * ViewHolder představuje jednu konkrétní položku (jeden řádek) v seznamu.
     * Drží reference na jednotlivé pohledy (TextView, ImageView) uvnitř této položky,
     * aby se nemusely neustále hledat pomocí `findViewById`.
     */
    class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Metoda, která naplní pohledy ViewHolderu daty z konkrétního `FoodItem` objektu.
         */
        fun bind(foodItem: FoodItem, onDeleteClicked: (FoodItem) -> Unit) {
            binding.textViewFoodName.text = foodItem.name
            binding.textViewFoodCalories.text = "${foodItem.calories} kcal"
            binding.textViewFoodType.text = "(${foodItem.type})"

            // Nastavíme listener na ikonu pro smazání.
            // Když se na ni klikne, zavolá se lambda funkce, kterou jsme dostali v konstruktoru adaptéru.
            binding.imageViewDelete.setOnClickListener { 
                onDeleteClicked(foodItem)
            }
        }
    }

    /**
     * Tato metoda je volána RecyclerViewem, když potřebuje vytvořit nový ViewHolder.
     * To se stane jen párkrát na začátku, pak se ViewHoldery recyklují.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        // "Nafoukneme" XML layout pro jednu položku (item_food.xml).
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    /**
     * Tato metoda je volána RecyclerViewem, když chce zobrazit data v konkrétním ViewHolderu.
     * To se děje pokaždé, když uživatel scrolluje a na obrazovku přichází nová položka.
     */
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        // Získáme položku dat pro danou pozici.
        val foodItem = getItem(position)
        // Zavoláme metodu `bind`, která naplní ViewHolder daty.
        holder.bind(foodItem, onDeleteClicked)
    }
}

/**
 * DiffUtil je mechanismus, který pomáhá ListAdapteru efektivně zjistit, co se v seznamu změnilo.
 * Místo toho, aby překreslil celý seznam, překreslí jen ty položky, které se skutečně změnily,
 * byly přidány nebo smazány. Je to mnohem rychlejší než starší metoda `notifyDataSetChanged()`.
 */
class FoodDiffCallback : DiffUtil.ItemCallback<FoodItem>() {
    /**
     * Porovnává, zda se jedná o stejnou položku (typicky podle ID).
     */
    override fun areItemsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Porovnává, zda jsou obsahy položek stejné. Volá se jen pokud `areItemsTheSame` vrátí true.
     */
    override fun areContentsTheSame(oldItem: FoodItem, newItem: FoodItem): Boolean {
        return oldItem == newItem
    }
}
