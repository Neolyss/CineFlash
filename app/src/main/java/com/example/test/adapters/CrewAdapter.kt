package com.example.test.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.data.movie.Director
import com.example.test.data.movie.Writer
import com.example.test.databinding.CastingViewBinding

class CrewAdapter(private val directors: List<Director>, private val writers: List<Writer>, private val fragmentCasting: Fragment) : RecyclerView.Adapter<CrewAdapter.CrewViewHolder>() {

    inner class CrewViewHolder(val binding: CastingViewBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
        val binding = CastingViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CrewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        if(position <= directors.size - 1) {
            val director = directors[position]
            holder.binding.nameActor.text = director.name
            holder.binding.roleActor.text = "Director"
        } else {
            val writer = writers[position - directors.size]
            holder.binding.nameActor.text = writer.name
            holder.binding.roleActor.text = "Writer"
        }
        Glide.with(holder.binding.actorImage).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvo0MYCFSRgeVTwS8xSFy72HV63S42edupDA&usqp=CAU").into(holder.binding.actorImage)
    }

    override fun getItemCount(): Int = directors.size + writers.size

}
