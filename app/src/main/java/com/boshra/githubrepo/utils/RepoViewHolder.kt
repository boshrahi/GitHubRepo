package com.boshra.githubrepo.utils

import android.support.v7.widget.RecyclerView
import android.view.View
import com.boshra.githubrepo.R
import com.boshra.githubrepo.dataModel.Repo
import kotlinx.android.synthetic.main.repo_view_holder.view.*

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(repo: Repo)
    {
        itemView.project_name_tv.text = repo.name
        itemView.description_tv.text = repo.description
        itemView.avatar_iv.setDefaultImageResId(R.drawable.ic_projects)
    }
}
