package com.boshra.githubrepo.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "repository")
class Repository(@PrimaryKey @NonNull var id:String,
                 var name: String?="Not Available",
                 var full_name: String?="Not Available",
                 var description: String?="Not Available...") {

}