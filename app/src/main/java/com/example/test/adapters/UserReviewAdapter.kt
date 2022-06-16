package com.example.test.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test.data.movie.Actor
import com.example.test.data.reviews.UserReviews
import com.example.test.databinding.ActorViewBinding
import com.example.test.databinding.ReviewViewBinding

class UserReviewAdapter(private val reviews: UserReviews, private val fragment: Fragment) : RecyclerView.Adapter<UserReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(val binding: ReviewViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ReviewViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews.items[position]

        holder.binding.title.text = review.title
        holder.binding.rate.text = review.rate
        holder.binding.content.text = review.content
    }

    override fun getItemCount(): Int = reviews.items.size
}