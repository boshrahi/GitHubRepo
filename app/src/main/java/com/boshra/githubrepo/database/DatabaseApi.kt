package com.boshra.githubrepo.database

import com.boshra.githubrepo.dataModel.Details

interface DatabaseApi {

    fun onSelectAllRepos(list: ArrayList<Details>)
    fun onDataOpError(error: String)
}