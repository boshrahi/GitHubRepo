package com.boshra.githubrepo.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import com.android.volley.toolbox.ImageLoader
import com.boshra.githubrepo.AppController
import com.boshra.githubrepo.R
import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.dataModel.Repo
import com.boshra.githubrepo.viewmodel.DetailCallback
import com.boshra.githubrepo.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.detail_content_layout.*
import kotlinx.android.synthetic.main.detail_header_layout.*
import kotlinx.android.synthetic.main.detail_readme_layout.*
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment(), DetailCallback, View.OnClickListener{

    val ARG_KEY: String = "DetailsFragment-details-key"
    lateinit var detailsViewModel: DetailsViewModel
    lateinit var repo : Repo
    lateinit var imageViewLoader: ImageLoader


    fun getInstance(repo: Repo) : DetailsFragment{
        var detailsFragment = DetailsFragment()
        var bundle = Bundle()
        bundle.putParcelable(ARG_KEY,repo)
        detailsFragment.arguments = bundle
        return detailsFragment
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onStart() {
        super.onStart()
        repo = arguments?.getParcelable<Repo>(ARG_KEY)!!
        detailsViewModel = DetailsViewModel(this)
        if (repo != null) {
            detailsViewModel.loadDReposDetails(repo,activity)
        }else{
            fragmentManager?.popBackStack()
        }
        imageViewLoader = AppController.getInstance(context!!.applicationContext).imageLoader
        detail_fork_layout.setOnClickListener(this)
        detail_issue_layout.setOnClickListener(this)
        detail_star_layout.setOnClickListener(this)

    }
    override fun onDetailsFetched(result: Details) {
        details_progressLoading.visibility = GONE

        detail_fork_tv.text = numberModifire(result.forks_count)
        detail_issue_tv.text = numberModifire(result.open_issues)
        detail_star_tv.text = numberModifire(result.stargazers_count)

        detail_developer_name_tv.text = result.owner?.login.toString()
        detail_project_name_tv.text = repo.name
        detail_des_tv.text = repo.description
        avatar_url_iv.setDefaultImageResId(R.drawable.ic_user)
        avatar_url_iv.setImageUrl(result.owner?.avatar_url,imageViewLoader)
    }

    private fun numberModifire(number: Int?): String? {
        if (number!! >=1000){
            val temp = number/1000
            val remain = (number%1000)%10
            return (temp.toString() +"."+ remain+"k")
        }else return number.toString()
    }

    override fun onDetailsFetchError(networkResponse: String) {
        println("Detail fetch error : = " + networkResponse)
        fragmentManager?.popBackStack()
    }
    override fun onClick(v: View?) {
        println("v?.id" + " Clicked")
    }
}
