package com.boshra.githubrepo.viewmodel

import android.support.v4.app.FragmentActivity
import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.dataModel.Repo
import com.boshra.githubrepo.model.DetailsApiService
import com.boshra.githubrepo.model.DownloadManager

open class DetailsViewModel(): DetailsApiService {

    lateinit var detailCallback: DetailCallback
    var downloadManager : DownloadManager?=null

        constructor(detailCallback: DetailCallback) : this() {
        this.detailCallback = detailCallback

    }
    override fun onDetailsFetched(result: Details) {
        detailCallback.onDetailsFetched(result)
    }

    override fun onDetailsDataFetchError(networkResponse: String) {
        detailCallback.onDetailsFetchError(networkResponse)
    }
    fun loadDReposDetails(repo :Repo,context:FragmentActivity?){
        downloadManager = DownloadManager(repo.full_name!!,context,this)
        downloadManager!!.loadSingleRepos(repo.full_name!!)
    }
    fun cancelDetailsRequest(){
        if (downloadManager!=null) downloadManager!!.cancelDetailsRequest()
    }
}
interface DetailCallback{
    fun onDetailsFetched(result: Details)
    fun onDetailsFetchError(networkResponse: String)
}