package com.boshra.githubrepo.model

import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.boshra.githubrepo.AppController
import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.dataModel.Repo
import com.boshra.githubrepo.utils.GsonParser


class DownloadManager(){


    var next_head: Int? = null
    var repo_fullname: String? = null
    var position: Int = 0
    var context: FragmentActivity? = null
    lateinit var volley: AppController
    lateinit var repoApiService: RepoApiService
    lateinit var detailsApiService: DetailsApiService
    val BASE_URL = "https://api.github.com/"
    val SINGLE_REPO = "SINGLE_REPO"


    constructor (next_head: Int, context: FragmentActivity?, repoApiService: RepoApiService) : this() {
        this.next_head = next_head
        this.context = context
        this.repoApiService = repoApiService
        volley = AppController.instance!!
        //volley = AppController.getInstance(this.context!!.applicationContext)

    }

    constructor(repo_fullname: String,context: FragmentActivity?, detailsApiService: DetailsApiService) : this() {
        this.repo_fullname = repo_fullname
        this.context = context
        this.detailsApiService = detailsApiService
        volley = AppController.instance!!
        //volley = AppController.getInstance(this.context!!.applicationContext)
    }


    fun loadAllRepos() {

        println("next head = " + next_head)
        val url = BASE_URL + "repositories?since="+next_head
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val arr: ArrayList<Repo> = GsonParser().parseTotalResponse(response)
                repoApiService.onRepositoryFetched(arr)
            },
            Response.ErrorListener { error -> repoApiService.onRepoDataFetchError("Error : "+
                    error.message) })
        stringRequest.tag = next_head
        volley.addToRequestQueue(stringRequest)
    }


    fun loadSingleRepos(singleRepAddress: String){
        val url = BASE_URL + "repos/"+singleRepAddress
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val arr: Details = GsonParser().parseSingleRepoResponse(response)
                detailsApiService.onDetailsFetched(arr)
            },
            Response.ErrorListener { error -> detailsApiService.onDetailsDataFetchError("Error : "+
                    error.message) })
        stringRequest.tag = SINGLE_REPO
        volley.addToRequestQueue(stringRequest)
    }
    fun cancelReposRequest(){
        println("volley next head = " + next_head)
        volley.cancelPendingRequests(this.context!!)

    }
    fun cancelDetailsRequest(){
        volley.cancelPendingRequests(this.SINGLE_REPO)

    }

}
