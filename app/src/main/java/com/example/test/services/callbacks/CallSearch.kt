package com.example.test.services.callbacks

import android.os.Handler
import android.os.Looper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

abstract class CallSearch : Callback {

    protected fun runOnUIThread(task: Runnable) {
        Handler(Looper.getMainLooper()).post(task);
    }

    override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
    }

}