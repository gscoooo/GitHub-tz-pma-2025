package com.example.myapp011

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptér nyní pracuje se seznamem objektů Mood
class MoodAdapter(private val moods: List<Mood>) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moodIcon: ImageView = itemView.findViewById(R.id.ivMoodIcon)
        val moodNote: TextView = itemView.findViewById(R.id.tvMoodNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mood, parent, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        // Nastavíme text z našeho Mood objektu
        holder.moodNote.text = moods[position].note
    }

    override fun getItemCount(): Int {
        return moods.size
    }
}
