package com.example.test.services.callbacks

import android.util.Log
import com.example.test.data.movie.Movie
import com.example.test.data.reviews.MetacriticReviews
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

abstract class CallMetacriticReview : CallSearch() {

    companion object {
        const val TAG : String = "Metacritic"
    }

    abstract fun fireOnResponse(data: MetacriticReviews)

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            val json: String = response.body!!.string()
            Log.d(TAG, json)
            val gson : Gson = Gson()
            val data: MetacriticReviews = gson.fromJson(json, MetacriticReviews::class.java)

            runOnUIThread(Runnable {
                fireOnResponse(data)
            })
        }
    }

}