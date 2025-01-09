package com.example.gitapp.ui.main.userRepository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapp.R
import com.example.gitapp.data.model.RepoResponse

class RepositoryAdapter(private var repoList: List<RepoResponse?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private val VIEW_TYPE_REPOSITORY = 1
  private val VIEW_TYPE_LOADING = 2

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return if (viewType == VIEW_TYPE_REPOSITORY) {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_list_item, parent, false)
      RepositoryViewHolder(view)
    } else {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.row_loading, parent, false)
      LoadingViewHolder(view)
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (holder is RepositoryViewHolder) {
      val repository = repoList[position]
      repository?.let { holder.bind(it) }
    }
  }

  override fun getItemCount(): Int = repoList.size

  override fun getItemViewType(position: Int): Int {
    return if (repoList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_REPOSITORY
  }

  fun updateData(newRepoList: List<RepoResponse>) {
    repoList = newRepoList
    notifyDataSetChanged()
  }

  fun showLoading() {
    repoList = repoList + null
    notifyItemInserted(repoList.size - 1)
  }

  fun hideLoading() {
    if (repoList.isNotEmpty() && repoList.last() == null) {
      val position = repoList.size - 1
      repoList = repoList.dropLast(1)
      notifyItemRemoved(position)
    }
  }

  class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val repoNameTextView: TextView = itemView.findViewById(R.id.tv_repo_name)

    fun bind(repository: RepoResponse) {
      repoNameTextView.text = repository.name
    }
  }

  class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

