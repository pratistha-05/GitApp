package com.example.gitapp.repository

import com.example.gitapp.data.model.UserData
import com.example.gitapp.di.RetrofitInstance
import com.example.gitapp.utils.Result
import retrofit2.Response

class UserRepository {

  suspend fun getUserRepositories(username: String): Result<List<UserData>> {
    return try {
      val response = RetrofitInstance.api.getUserRepositories(username)
      if (response.isSuccessful) {
        val repositories = response.body()
        if (!repositories.isNullOrEmpty()) {
          Result.Success(repositories)
        } else {
          Result.Error("No repositories found")
        }
      } else {
        Result.Error("Error: ${response.message()}")
      }
    } catch (e: Exception) {
      Result.Error("Exception: ${e.message}")
    }
  }
}

