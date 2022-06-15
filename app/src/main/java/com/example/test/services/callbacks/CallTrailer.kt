package com.example.test.services.callbacks

import android.util.Log
import com.example.test.data.movie.Movie
import com.example.test.data.trailer.YoutubeTrailer
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

abstract class CallTrailer : CallSearch() {

    abstract fun fireOnResponse(data: YoutubeTrailer)

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            val json: String = response.body!!.string()
            Log.d(CallMovie.TAG, json)
            val gson : Gson = Gson()
            val data: YoutubeTrailer = gson.fromJson(json, YoutubeTrailer::class.java)

            runOnUIThread(Runnable {
                fireOnResponse(data)
            })
        }
    }
}