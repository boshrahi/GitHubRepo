package com.boshra.githubrepo.viewmodel

import android.support.v4.app.FragmentActivity
import com.boshra.githubrepo.dataModel.Repo
import com.boshra.githubrepo.model.DownloadManager
import com.boshra.githubrepo.model.RepoApiService
import com.boshra.githubrepo.view.ListofRepoFragment

class ReposViewModel() : RepoApiService {

    lateinit var listCallback: ListCallback
    lateinit var downloadManager : DownloadManager

    constructor (listCallback: ListCallback) : this() {
        this.listCallback = listCallback

    }
    fun loadReposData(next_head:Int,context: FragmentActivity?){
        downloadManager = DownloadManager(next_head,context,this)
        downloadManager.loadAllRepos()
    }
    fun cancelReposRequest() {
        downloadManager.cancelReposRequest()
    }
    override fun onRepositoryFetched(repos: ArrayList<Repo>) {
        listCallback.onListFetched(repos)
    }

    override fun onRepoDataFetchError(networkResponse: String) {
        listCallback.onListFetchError(networkResponse)
    }

}
interface ListCallback{
    fun onListFetched(list: ArrayList<Repo>)
    fun onListFetchError(networkResponse: String)
}