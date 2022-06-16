package com.example.test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.R
import com.example.test.adapters.TrendAdapter
import com.example.test.data.trends.Trends
import com.example.test.databinding.FragmentMoviesBinding
import com.example.test.services.MovieService
import com.example.test.services.callbacks.CallTrend

/**
 * A simple [Fragment] subclass.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : Fragment() {

    companion object {
        private const val TAG = "Movies"
    }

    private var _binding: FragmentMoviesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val moviesFragment = this

        val movieService = context?.let { MovieService(it) }
        movieService?.getTrends("marvel", object : CallTrend() {
            override fun fireOnResponse(data: Trends) {
                binding.trends.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.trends.adapter = TrendAdapter(data, moviesFragment)
            }
        })
        // Inflate the layout for this fragment
        return binding.root
    }
}