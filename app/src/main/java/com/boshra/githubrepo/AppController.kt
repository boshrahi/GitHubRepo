package com.boshra.githubrepo

import android.app.Application
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.boshra.githubrepo.utils.LruBitmapCache

class AppController : Application() {

    private lateinit var mRequestQueue: RequestQueue
    private lateinit var mImageLoader: ImageLoader

    companion object {

        val TAG = AppController::class.java.simpleName

        @get:Synchronized
        var instance: AppController? = null
            private set
    }

    val requestQueue: RequestQueue
        get() {
            mRequestQueue = Volley.newRequestQueue(applicationContext)
            return mRequestQueue
        }

    val imageLoader: ImageLoader
        get() {
            requestQueue
            mImageLoader = ImageLoader(this.mRequestQueue, LruBitmapCache())
            return this.mImageLoader
        }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun <T> addToRequestQueue(req: Request<T>, tag: String) {
        // set the default tag if tag is empty
        req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue.add(req)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        req.tag = TAG
        requestQueue.add(req)
    }

    fun cancelPendingRequests(tag: Any) {
            mRequestQueue.cancelAll(tag)
            println("volley tag " + tag)

    }
}