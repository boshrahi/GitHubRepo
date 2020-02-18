package com.boshra.githubrepo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.boshra.githubrepo.R
import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.utils.EndlessListListener
import com.boshra.githubrepo.utils.OnViewHolderClickListener
import com.boshra.githubrepo.utils.RepoListAdapter
import com.boshra.githubrepo.viewmodel.DatabaseCallback
import com.boshra.githubrepo.viewmodel.DatabaseViewModel
import com.boshra.githubrepo.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.fragment_list_repos.*
import com.boshra.githubrepo.utils.Utils
import com.boshra.githubrepo.viewmodel.RepoViewModelFactory


class ListofRepoFragment : Fragment(), DatabaseCallback, OnViewHolderClickListener {

    var adapter: RepoListAdapter? = null
    var layoutManager: LinearLayoutManager? = null
    var BASE_PAGE = 100
    var BASE_PAGE_OFFSET = 11
    //var dataBaseViewModel: DatabaseViewModel? = null

    private val viewModel: ReposViewModel by lazy {
        ViewModelProvider(this, RepoViewModelFactory()).get(ReposViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_list_repos, container, false)
        viewModel.repoData.observe(this, Observer {
            if (null != it) {
                onListFetched(it)
            }
        })
        viewModel.itemData.observe(this, Observer {
            if (null != it){
                listItemChanged(it)
            }
        })
        return view
    }

    override fun onStart() {
        super.onStart()
        //dataBaseViewModel = DatabaseViewModel(this, activity!!)
        loadData()
    }

    //Network Callback-----------------------------------------------------
    fun listItemChanged(detail: Details) {
        adapter?.updateItemList(detail)
        //TODO update DB
        //updateDatabaseWithNewData(list)

    }
    fun onListFetched(list: ArrayList<Details>) {
        adapter?.updateRepoList(list)
        progressLayoutGone()
        setEndlessListEnable()
        //TODO update DB
        //updateDatabaseWithNewData(list)

    }
    fun onListFetchError(networkResponse: String) {
        if (isVisible) {
            println("Network Error : = " +networkResponse)
            progressLayoutGone()
        }
        Utils().showAlertDialog("Error","Something wrong with loading data.",this.requireContext())
    }
    // DataBase Callbacks---------------------------------------------------------
    override fun onDBDataFetch(list: ArrayList<Details>) {
        if (list.size == 0) {

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
    override fun onItemClicked(details: Details) {
//        if( Utils().networkAvailable(this.requireContext())){
//            val detailsFragment = DetailsFragment().getInstance(details)
//            val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
//            fragmentTransaction.addToBackStack("DetailsFragment")
//            fragmentTransaction.replace(R.id.main_layout, detailsFragment)
//            fragmentTransaction.commit()
//        }else{
//            Utils().showAlertDialog("Error","Something wrong with loading data.",this.requireContext())
//        }
    }

    //Useful methods----------------------------------------------------------
    private fun updateDatabaseWithNewData(list: ArrayList<Details>) {
        //dataBaseViewModel?.deleteAndInsertRepos(list)
    }

    private fun progressLayoutGone() {
        list_progress_bar.visibility = INVISIBLE
        endless_layout.visibility = GONE

    }

    private fun setListAdapter(list: List<Details>) {
        adapter = RepoListAdapter(activity!!, list as ArrayList<Details>,this)
        layoutManager = LinearLayoutManager(activity)
        list_repos_rv.layoutManager = layoutManager
        list_repos_rv.adapter = adapter
    }

    private fun setEndlessListEnable() {
        list_repos_rv.addOnScrollListener(object : EndlessListListener(list_repos_rv.layoutManager!!, 1) {
            override fun onLoadMore() {
                loadMoreData()
            }
        })
    }
    private fun loadData() {
        val emptyList = arrayListOf<Details>()
        setListAdapter(emptyList)
        //dataBaseViewModel?.getRecentRepos()
        list_progress_bar.visibility = VISIBLE
    }

    private fun loadMoreData() {
        endless_layout.visibility = VISIBLE
        BASE_PAGE =+ BASE_PAGE_OFFSET
        println("BASE_PAGE = " + BASE_PAGE)
        viewModel.loadReposData(BASE_PAGE)
    }
}
