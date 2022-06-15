package com.example.test.services.callbacks

import android.util.Log
import com.example.test.data.movie.Movie
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

abstract class CallMovie : CallSearch() {

    companion object {
        const val TAG : String = "Movie"
    }

    abstract fun fireOnResponse(data: Movie)

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            val json: String = response.body!!.string()
            Log.d(TAG, json)
            val gson : Gson = Gson()
            val data: Movie = gson.fromJson(json, Movie::class.java)

            runOnUIThread(Runnable {
                fireOnResponse(data)
            })
        }
    }
}