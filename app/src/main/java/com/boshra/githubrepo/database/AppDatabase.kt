package com.boshra.githubrepo.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Repository::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getRepoDAO() : RepoDAO
}