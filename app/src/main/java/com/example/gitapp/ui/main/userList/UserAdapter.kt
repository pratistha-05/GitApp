package com.example.gitapp.ui.main.userList

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitapp.R
import com.example.gitapp.data.model.UserData


class UserAdapter(private var usersList: List<UserData>,
  private val onUserClick: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  companion object {
    private const val VIEW_TYPE_USER = 1
    private const val VIEW_TYPE_LOADING = 2
  }

  private var isLoading = false

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return if (viewType == VIEW_TYPE_USER) {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
      ViewHolder(view)
    } else {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.row_loading, parent, false)
      LoadingViewHolder(view)
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (holder is ViewHolder) {
      val user = usersList[position]
      holder.bind(user)
      holder.itemView.setOnClickListener {
        onUserClick(user.username)
      }
    }
  }

  override fun getItemCount(): Int = usersList.size + if (isLoading) 1 else 0

  override fun getItemViewType(position: Int): Int {
    return if (position < usersList.size) VIEW_TYPE_USER else VIEW_TYPE_LOADING
  }

  fun updateData(userDataListItems: List<UserData>) {
    usersList = userDataListItems
    notifyDataSetChanged()
  }

  fun appendData(newUsers: List<UserData>) {
    val startPosition = usersList.size
    usersList = usersList + newUsers
    notifyItemRangeInserted(startPosition, newUsers.size)
  }

  fun showLoading() {
    if (!isLoading) {
      isLoading = true
      notifyItemInserted(usersList.size)
    }
  }

  fun hideLoading() {
    if (isLoading) {
      isLoading = false
      notifyItemRemoved(usersList.size)
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
    private val usernameTextView: TextView = itemView.findViewById(R.id.tv_username)
    private val githubURL: TextView = itemView.findViewById(R.id.tv_bio)
    private val followersTextView: TextView = itemView.findViewById(R.id.tv_followers)
    private val repositoriesTextView: TextView = itemView.findViewById(R.id.tv_repositories)

    fun bind(user: UserData) {
      usernameTextView.text = user.username
      githubURL.text = user.githubURL ?: "No bio available"
      followersTextView.text = "Followers: ${user.followers}"
      repositoriesTextView.text = "Repositories: ${user.following}"

      Glide.with(itemView.context)
        .load(user.avatar_url)
        .circleCrop()
        .into(avatarImageView)

      githubURL.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/${user.githubURL}"))
        itemView.context.startActivity(intent)
      }
    }
  }

  class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
