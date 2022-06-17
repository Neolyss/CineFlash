package com.example.test.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.MovieActivity
import com.example.test.data.history.History
import com.example.test.data.movie.Actor
import com.example.test.databinding.CastingViewBinding
import com.example.test.databinding.FilmHistoryCardBinding

class ActorAdapter(private val actors: List<Actor>, private val fragment: Fragment) : RecyclerView.Adapter<ActorAdapter.ActorViewHolder>() {

    inner class ActorViewHolder(val binding: CastingViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val binding = CastingViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ActorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = actors[position]

        holder.binding.nameActor.text = actor.name
        holder.binding.roleActor.text = actor.asCharacter
        Glide.with(holder.binding.actorImage).load(actor.image).into(holder.binding.actorImage)
    }

    override fun getItemCount(): Int = actors.size
}