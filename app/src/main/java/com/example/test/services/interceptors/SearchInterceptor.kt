package com.example.test.services.interceptors

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class SearchInterceptor (val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request : Request = chain.request()
        // see what request it is
        val url : String = request.url.toString()

        var file : String = ""
        if(url.contains("Search")) {
            Log.d("URL", "Search")
            file = "json/search.json"
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