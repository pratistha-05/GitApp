package com.example.gitapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapp.R
import com.example.gitapp.data.model.UserData

class UserAdapter(private var repositories: List<UserData>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

  fun updateData(newRepositories: List<UserData>) {
    repositories = newRepositories
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val repository = repositories[position]
    holder.bind(repository)
  }

  override fun getItemCount(): Int = repositories.size

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.tv_username)
    private val description: TextView = itemView.findViewById(R.id.tv_desc)
    private val language: TextView = itemView.findViewById(R.id.tv_stars)
    private val stars: TextView = itemView.findViewById(R.id.tv_stars)

    fun bind(repository: UserData) {
      name.text = repository.name
      description.text = repository.description ?: "No description"
      language.text = "Language: ${repository.language ?: "N/A"}"
      stars.text = "⭐ ${repository.stargazers_count}"
    }
  }
}
