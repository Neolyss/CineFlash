package com.example.test.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.MovieActivity
import com.example.test.adapters.TrendAdapter
import com.example.test.data.history.Film
import com.example.test.data.search.Result
import com.example.test.data.search.Search
import com.example.test.data.trends.Trends
import com.example.test.databinding.FragmentMoviesBinding
import com.example.test.services.MovieService
import com.example.test.services.SearchService
import com.example.test.services.callbacks.CallSearch
import com.example.test.services.callbacks.CallTrend
import com.example.test.services.callbacks.CallUserSearch

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
                binding.trendsMarvel.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.trendsMarvel.adapter = TrendAdapter(data, moviesFragment)
            }
        })

        movieService?.getTrends("star wars", object : CallTrend() {
            override fun fireOnResponse(data: Trends) {
                binding.trendsSW.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.trendsSW.adapter = TrendAdapter(data, moviesFragment)
            }
        })

        val searchService : SearchService = this.context?.let { SearchService(it) }!!

        binding.textField.setEndIconOnClickListener {
            val search = binding.textInput.text.toString()
            // Handle the search
            searchService.getSearch(search, object : CallUserSearch() {
                override fun fireOnResponse(data: Search) {
                    val firstFilm = data.results[0]
                    val intent : Intent = Intent(context, MovieActivity::class.java)
                    intent.putExtra("idFilm", firstFilm.id)
                    startActivity(intent)
                }
            })
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}