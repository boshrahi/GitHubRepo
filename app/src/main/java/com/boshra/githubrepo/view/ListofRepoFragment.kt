package com.boshra.githubrepo.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import com.boshra.githubrepo.R
import com.boshra.githubrepo.dataModel.Repo
import com.boshra.githubrepo.utils.EndlessListListener
import com.boshra.githubrepo.utils.OnViewHolderClickListener
import com.boshra.githubrepo.utils.RepoListAdapter
import com.boshra.githubrepo.viewmodel.DatabaseCallback
import com.boshra.githubrepo.viewmodel.DatabaseViewModel
import com.boshra.githubrepo.viewmodel.ListCallback
import com.boshra.githubrepo.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.fragment_list_repos.*
import com.boshra.githubrepo.utils.Utils


class ListofRepoFragment : Fragment(), ListCallback, DatabaseCallback, OnViewHolderClickListener {

    var BASE_PAGE = 300
    var adapter: RepoListAdapter? = null
    var layoutManager: LinearLayoutManager? = null
    var reposViewModel: ReposViewModel? = null
    var dataBaseViewModel: DatabaseViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.boshra.githubrepo.R.layout.fragment_list_repos, container, false)
    }

    override fun onStart() {
        super.onStart()
        reposViewModel = ReposViewModel(this)
        dataBaseViewModel = DatabaseViewModel(this, activity!!)
        loadData()
    }

    //Network Callback-----------------------------------------------------
    override fun onListFetched(list: ArrayList<Repo>) {
        if (isVisible){
            adapter?.updateRepoList(list)
            progressLayoutGone()
            setEndlessListEnable()
            updateDatabaseWithNewData(list)
        }

    }
    override fun onListFetchError(networkResponse: String) {
        if (isVisible) {
            println("Network Error : = " +networkResponse)
            progressLayoutGone()
        }
        Utils().showAlertDialog("Error","Something wrong with loading data.",this.requireContext())
    }
    // DataBase Callbacks---------------------------------------------------------
    override fun onDBDataFetch(list: ArrayList<Repo>) {
        if (list.size == 0) {
            reposViewModel?.loadReposData(BASE_PAGE, activity)
        } else {
            setListAdapter(list)
            progressLayoutGone()
            setEndlessListEnable()
        }
    }

    override fun onDBDataFetchError(response: String) {
        println("Database Error : = " + response)
        Utils().showAlertDialog("Error","Something wrong with loading data.",this.requireContext())
    }
    //List Click Callback---------------------------------------------------------------------
    override fun onItemClicked(repo: Repo) {
        if( Utils().networkAvailable(this.requireContext())){
            val detailsFragment = DetailsFragment().getInstance(repo)
            val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.addToBackStack("DetailsFragment")
            fragmentTransaction.replace(R.id.main_layout, detailsFragment)
            fragmentTransaction.commit()
            println(repo.toString())
        }else{
            Utils().showAlertDialog("Error","Something wrong with loading data.",this.requireContext())
        }
    }

    //Useful methods----------------------------------------------------------
    private fun updateDatabaseWithNewData(list: ArrayList<Repo>) {
        dataBaseViewModel?.deleteAndInsertRepos(list)
    }

    private fun progressLayoutGone() {
        list_progress_bar.visibility = INVISIBLE
        endless_layout.visibility = GONE

    }

    private fun setListAdapter(list: ArrayList<Repo>) {
        adapter = RepoListAdapter(activity!!, list,this)
        layoutManager = LinearLayoutManager(activity)
        list_repos_rv.layoutManager = layoutManager
        list_repos_rv.adapter = adapter
    }

    private fun setEndlessListEnable() {
        list_repos_rv.addOnScrollListener(object : EndlessListListener(list_repos_rv.layoutManager!!, 3) {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
    }
    private fun loadData() {
        val emptyList = arrayListOf<Repo>()
        setListAdapter(emptyList)
        dataBaseViewModel?.getRecentRepos()
        list_progress_bar.visibility = VISIBLE
    }

    private fun loadMoreData() {
        endless_layout.visibility = VISIBLE
        BASE_PAGE = BASE_PAGE + 100 //next 100 batch of repos
        reposViewModel?.loadReposData(BASE_PAGE, activity)
    }

    override fun onStop() {
        super.onStop()
        reposViewModel = null
        dataBaseViewModel = null

    }
}
