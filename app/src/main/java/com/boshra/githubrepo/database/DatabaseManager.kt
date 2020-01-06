package com.boshra.githubrepo.database

import android.arch.persistence.room.Room
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.boshra.githubrepo.dataModel.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class DatabaseManager() {

    lateinit var database: AppDatabase
    lateinit var databaseApi: DatabaseApi
    var context: FragmentActivity? = null

    constructor (context: FragmentActivity?, databaseApi: DatabaseApi) : this() {

        this.context = context
        this.databaseApi = databaseApi
        database = Room.databaseBuilder(
            context!!.applicationContext,
            AppDatabase::class.java, "db-repo"
        ).build()

    }

    fun insertRecentReposIntoDBAsync(list: ArrayList<Repo>) {
        DatabaseAsyncTask(databaseApi,database,DatabaseAsyncTask().TYPE_INSERT_ALL,list).execute()
    }

    fun getRecentReposDBAsync() {
        DatabaseAsyncTask(databaseApi,database,DatabaseAsyncTask().TYPE_SELECT_ALL,null).execute()
    }

    fun deleteAndInsertReposAsync(list: ArrayList<Repo>) {
        DatabaseAsyncTask(databaseApi,database,DatabaseAsyncTask().TYPE_DELETE_INSERT_ALL,list).execute()
    }


}