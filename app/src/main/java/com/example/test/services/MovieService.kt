package com.example.test.services

import android.content.Context
import android.util.Log
import com.example.test.BuildConfig
import com.example.test.services.callbacks.CallMovie
import com.example.test.services.callbacks.CallTrailer
import com.example.test.services.callbacks.CallTrend
import com.example.test.services.interceptors.FilmInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

class MovieService(context: Context) {

    companion object {
        private val TAG = "Movie"
    }

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(FilmInterceptor(context))
        .build()

    fun getMovie(filmId : String, callback: CallMovie) {
        val request: Request = Request.Builder()
            .url("http://imdb-api.com/en/API/Title/" + URLEncoder.encode(BuildConfig.API_KEY, "UTF-8") + "/" + URLEncoder.encode(filmId, "UTF-8") + "/Trailer,Ratings")
            .build()

        Log.d(TAG, request.url.toString())

        client.newCall(request).enqueue(callback)
    }

    fun getTrailer(filmId : String, callback: CallTrailer) {
        val request: Request = Request.Builder()
            .url("http://imdb-api.com/en/API/YouTubeTrailer/" + URLEncoder.encode(BuildConfig.API_KEY, "UTF-8") + "/" + URLEncoder.encode(filmId, "UTF-8"))
            .build()

        client.newCall(request).enqueue(callback)
    }

    fun getTrends(category: String, callback : CallTrend) {
        val request: Request = Request.Builder()
            .url(" https://imdb-api.com/API/AdvancedSearch/" + URLEncoder.encode(BuildConfig.API_KEY, "UTF-8") + "?keywords=" + URLEncoder.encode(category, "UTF-8"))
            .build()

        client.newCall(request).enqueue(callback)
    }
}
