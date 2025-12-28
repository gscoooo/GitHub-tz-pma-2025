package com.example.myapp011

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class AddMoodFragment : Fragment() {

    private lateinit var etNote: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_mood, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etNote = view.findViewById(R.id.etNote)
        val btnSave: Button = view.findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            val noteText = etNote.text.toString()
            if (noteText.isNotEmpty()) {
                // Přidáme novou náladu do sdíleného repozitáře
                MoodRepository.moods.add(Mood(noteText))
            }
            // Vrátíme se na předchozí obrazovku
            parentFragmentManager.popBackStack()
        }
    }
}
