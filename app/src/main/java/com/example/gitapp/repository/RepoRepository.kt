package com.example.gitapp.repository

import com.example.gitapp.data.api.ApiService
import com.example.gitapp.data.model.RepoResponse
import com.example.gitapp.utils.Result


class RepoRepository(private val api: ApiService) {

  suspend fun getUserRepositories(username: String): Result<List<RepoResponse>> {
    return try {
      val response = api.getUserRepositories(username)
      if (response.isSuccessful) {
        Result.Success(response.body() ?: emptyList())
      } else {
        Result.Error("Failed to fetch repositories")
      }
    } catch (e: Exception) {
      Result.Error("Exception occurred: ${e.message}")
    }
  }
}
