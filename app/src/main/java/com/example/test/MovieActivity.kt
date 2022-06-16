package com.example.test

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.test.data.movie.Movie
import com.example.test.data.trailer.YoutubeTrailer
import com.example.test.databinding.ActivityMovieBinding
import com.example.test.services.MovieService
import com.example.test.services.callbacks.CallMovie
import com.example.test.services.callbacks.CallTrailer
import com.google.android.material.chip.Chip
import com.google.android.youtube.player.*
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
        setContentView(binding.root)
        //setSupportActionBar(binding.toolbar)

        //val youtubePlayerView : YouTubePlayerView = binding.youtubePlayerView
       // lifecycle.addObserver(youtubePlayerView);

        binding.toolbar.setNavigationOnClickListener {
            val intent : Intent = Intent(this.baseContext, MainActivity::class.java)
            startActivity(intent)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_movie) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.selectedItemId = R.id.fragment1

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment1 -> {
                    Log.d("Navigation", "Nav to 1")
                    navController.navigate(R.id.seancesFragment)
                    true
                }
                R.id.fragment2 -> {
                    Log.d("Navigation", "Nav to 2")
                    navController.navigate(R.id.castingFragment)
                    true
                }
                R.id.fragment3 -> {
                    Log.d("Navigation", "Nav to 3")
                    navController.navigate(R.id.reviewsFragment)
                    true
                }
                R.id.fragment4 -> {
                    Log.d("Navigation", "Nav to 4")
                    navController.navigate(R.id.newsFragment)
                    true
                }
                else -> false
            }
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

                data.genreList.forEach() {
                    val chip = Chip(this@MovieActivity)
                    chip.text = it.key
                    chip.isCloseIconVisible = false
                    binding.genres.addView(chip)
                }

                binding.noteIMDB.text = data.imDbRating
                var numberVotes: String = data.imDbRatingVotes
                if(numberVotes.length > 3)
                    numberVotes = numberVotes.removeRange(2, numberVotes.length-1) + "k"
                binding.numberVoteIMDB.text = numberVotes
                binding.noteMetacritic.text = data.ratings.metacritic
                binding.noteTomato.text = data.ratings.rottenTomatoes
            }
        })

        movieService.getTrailer(idFilm, object : CallTrailer() {
            override fun fireOnResponse(data: YoutubeTrailer) {
                // Initialize youtube video

                /*youtubePlayerView.initialize(object :
                    AbstractYouTubePlayerListener() {
                    fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(data.videoId, 0)
                    }
                })*/
            }
        })
    }

}