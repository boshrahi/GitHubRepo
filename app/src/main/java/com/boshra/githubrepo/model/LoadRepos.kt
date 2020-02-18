package com.boshra.githubrepo.model

import android.util.Log
import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.view.ListofRepoFragment
import com.boshra.githubrepo.viewmodel.ReposViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

suspend fun loadDetailsChannels(
    service: GitHubService, next_head : Int,
    updateList: suspend (ArrayList<Details>) -> Unit,
    updateItem: suspend (Details) -> Unit) = coroutineScope {
    try {
        var repos = service.getAllRepos(next_head)
        var final_repos = ArrayList<Details>()
        for (r in 0..9){// for reduce size of repo list
            final_repos.add(repos.get(r))
        }
        val channel = Channel<Details>()
        updateList(final_repos)
        for (repo in final_repos) {
            launch {

                var s_index = repo.full_name!!.indexOf("/")
                var part1 = repo.full_name!!.substring(0, s_index)
                var part2 = repo.full_name!!.substring(s_index + 1,
                    repo.full_name!!.length)
                val details = service.getSingleRepo(part1, part2)
                channel.send(details)
            }
        }

        //var allDetails = ArrayList<Details>()
        repeat(final_repos.size) {
            val details = channel.receive()
            //allDetails.add(details)
            println("GithubRepo "+"project name = " + details.toString())
            updateItem(details)
        }
        //updateList(allDetails)

    }catch (e: Exception){
        Log.d("GithubRepo : ", e.message)
        updateList(ArrayList<Details>())
    }
}