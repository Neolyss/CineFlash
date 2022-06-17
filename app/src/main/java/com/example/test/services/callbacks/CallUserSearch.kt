package com.example.test.services.callbacks

import android.util.Log
import com.example.test.data.search.Search
import com.example.test.data.trailer.YoutubeTrailer
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

abstract class CallUserSearch : CallSearch() {

    abstract fun fireOnResponse(data: Search)

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            val json: String = response.body!!.string()
            Log.d(CallMovie.TAG, json)
            val gson : Gson = Gson()
            val data: Search = gson.fromJson(json, Search::class.java)

            runOnUIThread(Runnable {
                fireOnResponse(data)
            })
        }
    }
}