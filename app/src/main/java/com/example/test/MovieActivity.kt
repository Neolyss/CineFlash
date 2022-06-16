package com.example.test

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.test.data.movie.Movie
import com.example.test.data.trailer.YoutubeTrailer
import com.example.test.databinding.ActivityMovieBinding
import com.example.test.services.MovieService
import com.example.test.services.callbacks.CallMovie
import com.example.test.services.callbacks.CallTrailer
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MovieActivity : YouTubeBaseActivity() {

    private lateinit var _binding: ActivityMovieBinding

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMovieBinding.inflate(layoutInflater)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            val intent : Intent = Intent(this.baseContext, MainActivity::class.java)
            startActivity(intent)
        }

        // Retrieve id of the film
        val extras: Bundle? = intent.extras
        val idFilm : String = extras?.getString("idFilm")!!

        val movieService = MovieService(this.baseContext)

        movieService.getMovie(idFilm, object : CallMovie() {
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

                binding.noteIMDB.text = data.imDbRating
                binding.numberVoteIMDB.text = data.imDbRatingVotes
                binding.noteMetacritic.text = data.ratings.metacritic
                binding.noteTomato.text = data.ratings.rottenTomatoes
            }
        })

        movieService.getTrailer(idFilm, object : CallTrailer() {
            override fun fireOnResponse(data: YoutubeTrailer) {
                // Initialize youtube video
                val view : YouTubePlayerView = binding.trailer
                val listener : YouTubePlayer.OnInitializedListener = object : YouTubePlayer.OnInitializedListener {
                    override fun onInitializationSuccess(
                        provider : YouTubePlayer.Provider?,
                        player : YouTubePlayer?,
                        wasRestored : Boolean
                    ) {
                        player?.cueVideo(data.videoId)
                    }

                    override fun onInitializationFailure(
                        provider: YouTubePlayer.Provider?,
                        error : YouTubeInitializationResult?
                    ) {
                        Toast.makeText(baseContext, "Video loading failed", Toast.LENGTH_SHORT).show()
                    }
                }
                val apiKey = BuildConfig.YOUTUBE_API_KEY
                view.initialize(apiKey, listener)
            }
        })


    }

}