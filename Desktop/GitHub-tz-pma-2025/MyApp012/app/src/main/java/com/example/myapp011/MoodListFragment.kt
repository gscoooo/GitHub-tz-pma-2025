package com.example.myapp011

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random

class MoodListFragment : Fragment() {

    private val quotes = listOf(
        "Věř v sebe a všechno ostatní do sebe zapadne.",
        "Jediný způsob, jak dělat skvělou práci, je milovat to, co děláte.",
        "Každý den je nová příležitost.",
        "Štěstí není něco, co odkládáte na budoucnost; je to něco, co navrhujete pro současnost.",
        "Nečekejte. Čas nikdy nebude ten pravý."
    )

    private lateinit var rvMoods: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mood_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvQuote: TextView = view.findViewById(R.id.tvQuote)
        tvQuote.text = quotes[Random.nextInt(quotes.size)]

        rvMoods = view.findViewById(R.id.rvMoods)
        rvMoods.layoutManager = LinearLayoutManager(context)

        val fabAddMood: FloatingActionButton = view.findViewById(R.id.fabAddMood)
        fabAddMood.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, AddMoodFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    // Tato metoda se volá pokaždé, když se na fragment vrátíme.
    override fun onResume() {
        super.onResume()
        // Znovu načteme data z repozitáře a aktualizujeme adaptér.
        rvMoods.adapter = MoodAdapter(MoodRepository.moods)
    }
}
