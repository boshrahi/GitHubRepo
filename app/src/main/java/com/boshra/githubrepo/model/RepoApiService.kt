package com.boshra.githubrepo.model

import com.boshra.githubrepo.dataModel.Repo

interface RepoApiService {
    fun onRepositoryFetched(repos : ArrayList<Repo>)
    fun onRepoDataFetchError(networkResponse: String)
}