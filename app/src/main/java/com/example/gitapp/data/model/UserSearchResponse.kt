package com.example.gitapp.data.model

data class UserSearchResponse(
  val total_count: Int,
  val items: List<UserDataListItem>
)

data class UserDataListItem(
  val login: String,
  val avatar_url: String,
  val html_url: String,
  val followers_url: String,
  val following_url: String,
  val type: String,
  val score: Double
)