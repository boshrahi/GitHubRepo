package com.boshra.githubrepo.utils

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.boshra.githubrepo.R
import com.boshra.githubrepo.dataModel.Details

import kotlinx.android.synthetic.main.repo_view_holder.view.*
import android.util.Log
import androidx.annotation.RequiresApi
import android.annotation.TargetApi
import java.util.*
import kotlin.collections.ArrayList


class RepoListAdapter(var context: FragmentActivity, var repoList: ArrayList<Details>,
                      val itemClickListener: OnViewHolderClickListener):
    ListAdapter<Details, RepoViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RepoViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.repo_view_holder, parent, false)
        val holder  =  RepoViewHolder(view)
        view.constraintLayout.setOnClickListener{
            val repo = repoList.get(holder.adapterPosition)
            itemClickListener.onItemClicked(repo)
        }
        return holder

    }
    companion object DiffCallback : DiffUtil.ItemCallback<Details>() {
        override fun areItemsTheSame(oldItem: Details, newItem: Details): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Details, newItem: Details): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(viewHolder: RepoViewHolder, position: Int) {

        viewHolder.bind(repoList[position])
        //val avatar_url = "https://avatars2.githubusercontent.com/u/128?v=4"
        //if (avatar_url!=null) viewHolder.itemView.avatar_iv.setImageUrl(avatar_url, AppController.instance?.imageLoader)
    }

    fun updateRepoList(list: ArrayList<Details>){
        repoList.addAll(list)
        submitList(repoList)
        notifyDataSetChanged()
    }

    fun updateItemList(detail: Details) {
        var index = -1
        for ((i,listItem) in repoList.withIndex()){
            if (listItem.id == detail.id && listItem.forks_count == 0){
                index = i
                break
            }
        }
        println("GithubRepo "+"Detail item = "+detail.toString())
        repoList[index].forks_count = detail.forks_count
        repoList[index].stargazers_count = detail.stargazers_count
        notifyItemChanged(index)
    }

}
interface OnViewHolderClickListener{
    fun onItemClicked(repo: Details)
}

