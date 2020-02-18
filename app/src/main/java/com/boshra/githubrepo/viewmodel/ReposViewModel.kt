package com.boshra.githubrepo.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.model.GitHubService
import com.boshra.githubrepo.model.createGitHubService
import com.boshra.githubrepo.model.loadDetailsChannels
import kotlinx.coroutines.*


class ReposViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    lateinit var service: GitHubService


    private val _repoData = MutableLiveData<ArrayList<Details>>()
    val repoData: LiveData<ArrayList<Details>>
        get() = _repoData

    private val _itemData = MutableLiveData<Details>()
    val itemData: LiveData<Details>
        get() = _itemData

    init {
        println("initial of viewModel")
        loadReposData(100)
    }

    fun loadReposData(next_head: Int) {
        service = createGitHubService()

        coroutineScope.launch {
            loadDetailsChannels(service, next_head,
                { repos_details ->
                    withContext(Dispatchers.Main) {
                        _repoData.value = repos_details
                    }
                },
                { item_data ->
                    withContext(Dispatchers.Main) {
                        _itemData.value = item_data
                    }
                })
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
