package com.boshra.githubrepo.utils

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.boshra.githubrepo.R
import com.boshra.githubrepo.dataModel.Repo

import kotlinx.android.synthetic.main.repo_view_holder.view.*


class RepoListAdapter(var context: FragmentActivity,var repoList: ArrayList<Repo>,val itemClickListener: OnViewHolderClickListener):
    RecyclerView.Adapter<RepoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RepoViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.repo_view_holder, parent, false)
        val holder  =  RepoViewHolder(view)
        view.constraintLayout.setOnClickListener(View.OnClickListener {
            val repo = repoList.get(holder.adapterPosition)
            itemClickListener.onItemClicked(repo)
        })
        return holder

    }


    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {

        viewHolder.bind(repoList[position])
        //val avatar_url = "https://avatars2.githubusercontent.com/u/128?v=4"
        //if (avatar_url!=null) viewHolder.itemView.avatar_iv.setImageUrl(avatar_url, AppController.instance?.imageLoader)
    }

    fun updateRepoList(list: ArrayList<Repo>){
        repoList.addAll(list)
        notifyDataSetChanged()
    }

}
interface OnViewHolderClickListener{
    fun onItemClicked(repo: Repo)
}

