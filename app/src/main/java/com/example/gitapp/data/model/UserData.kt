package com.example.gitapp.data.model

data class UserData(
  val avatar_url: String,
  val login: String,
  val bio: String?,
  val followers: Int,
  val following: Int,
  val public_repos: Int
)

