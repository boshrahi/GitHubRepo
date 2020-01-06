package com.boshra.githubrepo.database

import android.os.AsyncTask
import com.boshra.githubrepo.dataModel.Repo


class DatabaseAsyncTask() : AsyncTask<Void, Void, Void>() {


    val TYPE_INSERT_ALL: Int = 0
    val TYPE_DELETE_INSERT_ALL: Int = 1
    val TYPE_SELECT_ALL: Int = 2

    lateinit var databaseApi: DatabaseApi
    lateinit var database: AppDatabase
    var list: ArrayList<Repo>? = null
    var errorString: String? = null
    lateinit var selectResult: List<Repository>
    var type: Int = -1

    constructor(databaseApi: DatabaseApi, database: AppDatabase, type: Int, list: ArrayList<Repo>?) : this() {
        this.databaseApi = databaseApi
        this.database = database
        this.type = type
        this.list = list
    }


    override fun onPreExecute() {
        super.onPreExecute()

    }

    override fun doInBackground(vararg params: Void?): Void? {
        when (type) {
            TYPE_INSERT_ALL -> {
                val array = createArrayOfData(list!!)
                val repoDao = database.getRepoDAO()
                try {
                    repoDao.insertAllRepos(*array)
                } catch (error: Exception) {
                    errorString = "insertAllReposDB " + error.message.toString()
                }
            }
            TYPE_DELETE_INSERT_ALL -> {
                try {
                    val repoDao = database.getRepoDAO()
                    repoDao.deleteAllRepos()
                } catch (error: Exception) {
                    errorString = "deleteRecentReposDB " + error.message.toString()
                }
            }
            TYPE_SELECT_ALL -> {
                val repoDao = database.getRepoDAO()
                try {
                    val result = repoDao.getRecentRepos()
                    selectResult = result
                } catch (error: Exception) {
                    errorString = "getRecentReposDB " + error.message.toString()

                }
            }
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        when (type) {
            TYPE_INSERT_ALL -> {
                if (errorString != null) databaseApi.onDataOpError(errorString!!)
            }
            TYPE_DELETE_INSERT_ALL -> {
                if (errorString != null) databaseApi.onDataOpError(errorString!!)
            }
            TYPE_SELECT_ALL -> {
                if (errorString != null) databaseApi.onDataOpError(errorString!!)
                else databaseApi.onSelectAllRepos(resultModifier(selectResult)!!)

            }
        }
    }


    fun createArrayOfData(list: ArrayList<Repo>): Array<Repository?> {
        val size = list.size
        val array = arrayOfNulls<Repository>(size)
        for (i in 0 until size) {
            val repo = list.get(i)
            array[i] = Repository(
                i.toString(), repo.name, repo.full_name, repo.description
            )
        }
        return array
    }

    fun resultModifier(result: List<Repository>): ArrayList<Repo>? {
        val modified = ArrayList<Repo>()
        for (i in 0 until result.size) {
            val repository = result.get(i)
            modified.add(
                Repo(
                    repository.id.toInt(),
                    repository.name,
                    repository.full_name,
                    repository.description
                )
            )
        }
        return modified
    }
}