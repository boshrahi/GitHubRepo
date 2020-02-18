package com.boshra.githubrepo.model

import androidx.fragment.app.FragmentActivity


class DownloadManager() {


    var next_head: Int? = null
    var repo_fullname: String? = null
    var context: FragmentActivity? = null
    //lateinit var requestQueue: RequestQueue
    lateinit var repoApiService: RepoApiService
    lateinit var detailsApiService: DetailsApiService
    val BASE_URL = "https://api.github.com/"
    val SINGLE_REPO = "SINGLE_REPO"


    constructor (next_head: Int, context: FragmentActivity?, repoApiService: RepoApiService) : this() {
//        this.next_head = next_head
//        this.context = context
//        this.repoApiService = repoApiService
//        requestQueue = AppController.getInstance(context!!.applicationContext).requestQueue

    }

    constructor(repo_fullname: String, context: FragmentActivity?, detailsApiService: DetailsApiService) : this() {
//        this.repo_fullname = repo_fullname
//        this.context = context
//        this.detailsApiService = detailsApiService
//        requestQueue = AppController.getInstance(context!!.applicationContext).requestQueue
    }


    fun loadAllRepos() {

//        val url = BASE_URL + "repositories?since=" + next_head
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                val arr: ArrayList<Repo> = GsonParser().parseTotalResponse(response)
//                repoApiService.onRepositoryFetched(arr)
//            },
//            Response.ErrorListener { error ->
//                repoApiService.onRepoDataFetchError(
//                    "Error : " +
//                            error.message
//                )
//            })
//        stringRequest.tag = next_head
//        requestQueue.add(stringRequest)
    }


    fun loadSingleRepos(singleRepAddress: String) {
//        val url = BASE_URL + "repos/" + singleRepAddress
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                val arr: Details = GsonParser().parseSingleRepoResponse(response)
//                detailsApiService.onDetailsFetched(arr)
//            },
//            Response.ErrorListener { error ->
//                detailsApiService.onDetailsDataFetchError(
//                    "Error : " +
//                            error.message
//                )
//            })
//        stringRequest.tag = SINGLE_REPO
//        requestQueue.add(stringRequest)
    }

    fun cancelReposRequest() {
        //requestQueue.cancelAll(next_head)

    }

    fun cancelDetailsRequest() {
        //requestQueue.cancelAll(this.SINGLE_REPO)

    }

}
