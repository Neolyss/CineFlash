package com.example.test.services

import android.content.Context
import com.example.test.BuildConfig
import com.example.test.data.reviews.UserReviews
import com.example.test.services.callbacks.CallMetacriticReview
import com.example.test.services.callbacks.CallMovie
import com.example.test.services.callbacks.CallUserReview
import com.example.test.services.interceptors.ReviewInterceptor
import okhttp3.OkHttpClient;
import okhttp3.Request
import java.net.URLEncoder

class ReviewService(context: Context)  {

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        //.addInterceptor(ReviewInterceptor(context))
        .build()

    fun getUserReviews(filmId: String, callback: CallUserReview) {
        val request: Request = Request.Builder()
            .url("http://imdb-api.com/en/API/Reviews/" + URLEncoder.encode(BuildConfig.API_KEY, "UTF-8") + "/" + URLEncoder.encode(filmId, "UTF-8"))
            .build()

        client.newCall(request).enqueue(callback)
    }

    fun getMetacriticReviews(filmId : String, callback: CallMetacriticReview) {
        val request: Request = Request.Builder()
            .url("http://imdb-api.com/en/API/MetacriticReviews/" + URLEncoder.encode(BuildConfig.API_KEY, "UTF-8") + "/" + URLEncoder.encode(filmId, "UTF-8"))
            .build()

        client.newCall(request).enqueue(callback)
    }
}
