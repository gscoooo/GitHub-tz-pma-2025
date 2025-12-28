package com.example.myapp007bfragmentsexample1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class PictureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.picture_fragment, container, false)

        val backToFactButton: Button = view.findViewById(R.id.back_to_fact_button)
        backToFactButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}