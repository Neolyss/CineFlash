package com.example.test.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.MovieActivity
import com.example.test.data.history.History
import com.example.test.data.trends.Trends
import com.example.test.databinding.FilmCardBinding
import com.example.test.databinding.FilmHistoryCardBinding
import com.example.test.databinding.FragmentHistoryBinding

class HistoryAdapter(private val history: History, private val fragment: Fragment) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding: FilmHistoryCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = FilmHistoryCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val film = history.history[position]

        holder.binding.filmTitle.text = film.title

        Glide.with(holder.binding.thumbnailHistory).load(film.image).into(holder.binding.thumbnailHistory)

        holder.binding.card.setOnClickListener {
            val intent : Intent = Intent(this.fragment.context, MovieActivity::class.java)
            intent.putExtra("idFilm", film.id)
            this.fragment.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = history.count
}