package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.test.data.movie.Movie
import com.example.test.data.trailer.YoutubeTrailer
import com.example.test.databinding.ActivityMovieBinding
import com.example.test.services.MovieService
import com.example.test.services.callbacks.CallMovie
import com.example.test.services.callbacks.CallTrailer
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MovieActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMovieBinding

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMovieBinding.inflate(layoutInflater)
        val movieService = MovieService(this.baseContext)

        movieService.getMovie("tt3896198", object : CallMovie() {
            override fun fireOnResponse(data: Movie) {
                Log.d("Test", data.toString())
                binding.title.text = data.title
                val date = LocalDate.parse(data.releaseDate)
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                val formattedDate = date.format(formatter)

                var sdf = SimpleDateFormat("mm")

                try {
                    val dt: Date? = sdf.parse(data.runtimeMins)
                    sdf = SimpleDateFormat("H'h'mm")
                    val duration = dt?.let { sdf.format(it) }

                    binding.datetime.text = "$formattedDate · $duration"
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                // Get youtube video
                movieService.getTrailer("tt3896198", object : CallTrailer() {
                    override fun fireOnResponse(data: YoutubeTrailer) {
                        // Initialize youtube video
                    }
                })

                binding.noteIMDB.text = data.imDbRating
                binding.numberVoteIMDB.text = data.imDbRatingVotes
                binding.noteMetacritic.text = data.ratings.metacritic
                binding.noteTomato.text = data.ratings.rottenTomatoes
            }
        })

        setContentView(binding.root)
    }
}