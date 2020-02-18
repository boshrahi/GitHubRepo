package com.boshra.githubrepo.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boshra.githubrepo.R
import com.boshra.githubrepo.dataModel.Details
import com.boshra.githubrepo.dataModel.Repo
import kotlinx.android.synthetic.main.repo_view_holder.view.*

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(details: Details)
    {
        itemView.project_name_tv.text = details.name
        itemView.description_tv.text = details.description
        itemView.fork_count_tv.text = details.forks_count.toString()
        itemView.start_count_tv.text = details.stargazers_count.toString()
        itemView.avatar_iv.setImageResource(R.drawable.ic_projects)
    }
}
