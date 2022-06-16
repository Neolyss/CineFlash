package com.example.test.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View.inflate
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.test.MovieActivity
import com.example.test.data.trends.Trends
import com.example.test.databinding.ActivityMainBinding.inflate
import com.example.test.databinding.FilmCardBinding
import com.example.test.databinding.FragmentMoviesBinding

class TrendAdapter(private val trends : Trends, private val fragment: Fragment) : RecyclerView.Adapter<TrendAdapter.TrendViewHolder>() {

    inner class TrendViewHolder(val binding: FilmCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        val binding = FilmCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TrendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        val trend = trends.results[position]

        holder.binding.filmTitle.text = trend.title
        val rating = trend.imDbRating
        if(rating == null) {
            holder.binding.rating.text = "Incoming"
        } else {
            holder.binding.rating.text = "${trend.imDbRating} / 10"
        }

        Glide.with(holder.binding.thumbnail).load(trend.image).into(holder.binding.thumbnail)

        holder.binding.card.setOnClickListener {
            val intent : Intent = Intent(this.fragment.context, MovieActivity::class.java)
            intent.putExtra("idFilm", trend.id)
            this.fragment.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = trends.results.size
}