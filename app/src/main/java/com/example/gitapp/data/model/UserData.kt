package com.example.gitapp.data.model

data class UserData(
  val avatar_url: String,
  val username: String,
  val githubURL: String?,
  val followers: Int,
  val following: Int,
  val public_repos: Int
)

