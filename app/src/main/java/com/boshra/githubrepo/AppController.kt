package com.boshra.githubrepo

import android.app.Application
import android.content.Context
import android.text.TextUtils
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.boshra.githubrepo.utils.LruBitmapCache

class AppController : Application() {

//    private lateinit var mRequestQueue: RequestQueue
//    private lateinit var mImageLoader: ImageLoader

    companion object {
        val TAG = AppController::class.java.simpleName
        @Volatile
        private var INSTANCE: AppController? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppController().also {
                    INSTANCE = it
                }
            }
    }
    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(applicationContext)
    }

//    val requestQueue: RequestQueue
//        get() {
//            mRequestQueue = Volley.newRequestQueue(applicationContext)
//            return mRequestQueue
//        }

//    val imageLoader: ImageLoader
//        get() {
//            requestQueue
//            mImageLoader = ImageLoader(this.mRequestQueue, LruBitmapCache())
//            return this.mImageLoader
//        }
    val imageLoader: ImageLoader by lazy {
        ImageLoader(requestQueue,LruBitmapCache())
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
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
        requestQueue.cancelAll(tag)

    }
}