package com.boshra.githubrepo.database

import android.arch.persistence.room.*


@Dao
interface RepoDAO {

    @Insert
    fun insertAllRepos(vararg repository : Repository?)

    @Delete
    fun deleteRepos(vararg repository : Repository)

    @Transaction
    @Query("SELECT id,name,full_name,description FROM repository")
    fun getRecentRepos() : List<Repository>

    @Query("DELETE FROM repository")
    fun deleteAllRepos()

    @Transaction
    fun deleteAndInsertRepos(repository : Array<Repository?>) {
        deleteAllRepos()
        insertAllRepos(*repository)
    }
}