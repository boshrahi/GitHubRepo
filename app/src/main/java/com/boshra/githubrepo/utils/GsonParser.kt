package com.boshra.githubrepo.utils

import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.dataModel.Repo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GsonParser {

    fun parseTotalResponse(response: String?): ArrayList<Repo> {
        val sType = object : TypeToken<ArrayList<Repo>>() { }.type
        return Gson().fromJson<ArrayList<Repo>>(response, sType)
    }
    fun parseSingleRepoResponse(response: String?): Details {
        return Gson().fromJson(response, Details::class.java)
    }

}