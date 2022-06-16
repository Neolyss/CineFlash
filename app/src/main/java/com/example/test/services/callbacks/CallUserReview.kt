package com.example.test.services.callbacks

import android.util.Log
import com.example.test.data.reviews.MetacriticReviews
import com.example.test.data.reviews.UserReviews
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

abstract class CallUserReview : CallSearch() {

    companion object {
        const val TAG : String = "UserReview"
    }

    abstract fun fireOnResponse(data: UserReviews)

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            val json: String = response.body!!.string()
            Log.d(TAG, json)
            val gson : Gson = Gson()
            val data: UserReviews = gson.fromJson(json, UserReviews::class.java)

            runOnUIThread(Runnable {
                fireOnResponse(data)
            })
        }
    }

}