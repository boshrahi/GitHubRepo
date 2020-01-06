package com.boshra.githubrepo.viewmodel

import android.support.v4.app.FragmentActivity
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
    fun deleteAndInsertRepos(list: ArrayList<Repo>){
        val subList = ArrayList<Repo>(list.subList(0, list.size-1))
        val databaseManager : DatabaseManager = DatabaseManager(context,this)
        databaseManager.deleteAndInsertReposAsync(subList)
    }
    override fun onSelectAllRepos(list: ArrayList<Repo>) {
        databaseCallback.onDBDataFetch(list)
    }

    override fun onDataOpError(error: String) {
        databaseCallback.onDBDataFetchError(error)
    }

}
interface DatabaseCallback{
    fun onDBDataFetch(list: ArrayList<Repo>)
    fun onDBDataFetchError(response: String)
}