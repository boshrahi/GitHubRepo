package com.boshra.githubrepo.database

import com.boshra.githubrepo.dataModel.Repo

interface DatabaseApi {

    fun onSelectAllRepos(list: ArrayList<Repo>)
    fun onDataOpError(error: String)
}