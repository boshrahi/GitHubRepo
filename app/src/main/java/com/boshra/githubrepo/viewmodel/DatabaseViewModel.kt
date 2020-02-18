package com.boshra.githubrepo.viewmodel

import androidx.fragment.app.FragmentActivity
import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.dataModel.Repo
import com.boshra.githubrepo.database.DatabaseApi
import com.boshra.githubrepo.database.DatabaseManager

class DatabaseViewModel() : DatabaseApi {

    lateinit var databaseCallback: DatabaseCallback
    lateinit var context: FragmentActivity

    constructor(databaseCallback: DatabaseCallback,context: FragmentActivity) : this() {
        this.databaseCallback = databaseCallback
        this.context = context

    }
    fun getRecentRepos(){
        val databaseManager : DatabaseManager = DatabaseManager(context,this)
        databaseManager.getRecentReposDBAsync()

    }
    fun deleteAndInsertRepos(list: ArrayList<Details>){
        val subList = ArrayList<Details>(list.subList(0, list.size-1))
        val databaseManager : DatabaseManager = DatabaseManager(context,this)
        databaseManager.deleteAndInsertReposAsync(subList)
    }
    override fun onSelectAllRepos(list: ArrayList<Details>) {
        databaseCallback.onDBDataFetch(list)
    }

    override fun onDataOpError(error: String) {
        databaseCallback.onDBDataFetchError(error)
    }

}
interface DatabaseCallback{
    fun onDBDataFetch(list: ArrayList<Details>)
    fun onDBDataFetchError(response: String)
}