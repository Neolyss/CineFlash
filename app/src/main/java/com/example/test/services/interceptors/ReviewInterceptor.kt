package com.example.test.services.interceptors

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException


class ReviewInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request : Request = chain.request()
        // see what request it is
        val url : String = request.url.toString()

        var file : String = ""
        if(url.contains("MetacriticReviews")) {
            Log.d("URL", "MetacriticReviews")
            file = "json/metacriticReviews.json"
        } else if(url.contains("Reviews")) {
            Log.d("URL", "Reviews")
            file = "json/userReviews.json"
        }

        lateinit var jsonString: String
        try {
            jsonString = this.context.assets.open(file)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        return Response.Builder()
            .code(200)
            .message(jsonString)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(jsonString.toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("content-type", "application/json")
            .build()
    }
}