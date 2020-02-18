package com.boshra.githubrepo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

@Entity(tableName = "repository")
class Repository(@PrimaryKey @NonNull var id:String,
                 var name: String?="Not Available",
                 var full_name: String?="Not Available",
                 var description: String?="Not Available...") {

}