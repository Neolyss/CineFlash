package com.example.test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.adapters.MetacriticReviewAdapter
import com.example.test.adapters.UserReviewAdapter
import com.example.test.data.reviews.MetacriticReviews
import com.example.test.data.reviews.UserReviews
import com.example.test.databinding.FragmentReviewsBinding
import com.example.test.services.ReviewService
import com.example.test.services.callbacks.CallMetacriticReview
import com.example.test.services.callbacks.CallUserReview

class ReviewsFragment : Fragment() {

    companion object {
        private const val TAG = "Casting"
    }

    private var _binding: FragmentReviewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewsBinding.inflate(inflater, container, false)

        val reviewsFragment = this
        val idFilm = arguments!!.get("idFilm") as String
        val reviewService : ReviewService = this.context?.let { ReviewService(it) }!!


        reviewService.getUserReviews(idFilm, object : CallUserReview() {
            override fun fireOnResponse(data: UserReviews) {
                binding.userReviews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.userReviews.adapter = UserReviewAdapter(data, reviewsFragment)
            }
        })

        reviewService.getMetacriticReviews(idFilm, object : CallMetacriticReview() {
            override fun fireOnResponse(data: MetacriticReviews) {
                binding.reviews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.reviews.adapter = MetacriticReviewAdapter(data, reviewsFragment)
            }
        })

        return binding.root
    }
}