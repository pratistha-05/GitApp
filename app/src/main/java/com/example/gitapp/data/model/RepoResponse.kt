package com.example.gitapp.data.model

data class RepoResponse(
  val name: String,
  val description: String?,
  val language: String?,
  val forks_count:Int,
  val stargazers_count:Int
)