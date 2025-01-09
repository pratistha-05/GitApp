package com.example.gitapp.ui.main

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


class UserAdapter(private var usersList: List<UserData>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val user = usersList[position]
    holder.bind(user)
  }
  fun updateData(userDataListItems: List<UserData>) {
    usersList=userDataListItems
  }

  fun appendData(newUsers: List<UserData>) {
    val startPosition = usersList.size
    usersList = usersList + newUsers
    notifyItemRangeInserted(startPosition, newUsers.size)
  }

  override fun getItemCount(): Int = usersList.size

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
}
