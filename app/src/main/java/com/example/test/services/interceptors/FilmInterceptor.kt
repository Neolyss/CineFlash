package com.example.test.services.interceptors

import android.app.Application
import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException


class FilmInterceptor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request : Request = chain.request()
        // see what request it is
        val url : String = chain.request().url.toString()

        lateinit var file : String
        if(url.contains("Title")) {
            file = "json/film.json"
        } else if(url.contains("YoutubeTrailer")) {
            file = "json/trailer.json"
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