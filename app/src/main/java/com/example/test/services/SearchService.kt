package com.example.test.services

import android.content.Context
import com.example.test.BuildConfig
import com.example.test.services.callbacks.CallSearch
import com.example.test.services.callbacks.CallUserReview
import com.example.test.services.callbacks.CallUserSearch
import com.example.test.services.interceptors.ReviewInterceptor
import com.example.test.services.interceptors.SearchInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

class SearchService(context: Context) {

    private val client: OkHttpClient = OkHttpClient
        .Builder()
        //.addInterceptor(SearchInterceptor(context))
        .build()

    fun getSearch(search: String, callback: CallSearch) {
        val request: Request = Request.Builder()
            .url("http://imdb-api.com/en/API/Search/" + URLEncoder.encode(BuildConfig.API_KEY, "UTF-8") + "/" + search)
            .build()

        client.newCall(request).enqueue(callback)
    }
}