package com.boshra.githubrepo.database

import androidx.room.Room
import androidx.fragment.app.FragmentActivity
import com.boshra.githubrepo.dataModel.Details


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

    fun insertRecentReposIntoDBAsync(list: ArrayList<Details>) {
        DatabaseAsyncTask(databaseApi,database,DatabaseAsyncTask().TYPE_INSERT_ALL,list).execute()
    }

    fun getRecentReposDBAsync() {
        DatabaseAsyncTask(databaseApi,database,DatabaseAsyncTask().TYPE_SELECT_ALL,null).execute()
    }

    fun deleteAndInsertReposAsync(list: ArrayList<Details>) {
        DatabaseAsyncTask(databaseApi,database,DatabaseAsyncTask().TYPE_DELETE_INSERT_ALL,list).execute()
    }


}