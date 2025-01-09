package com.example.gitapp.repository

import com.example.gitapp.data.api.ApiService
import com.example.gitapp.data.model.UserData
import com.example.gitapp.data.model.UserDataListItem
import com.example.gitapp.data.model.UserSearchResponse
import com.example.gitapp.di.RetrofitInstance
import com.example.gitapp.utils.Result
import retrofit2.Response

class UserRepository() {
  suspend fun searchUsers(username: String): Result<List<UserDataListItem>> {
    return try {
      val response = RetrofitInstance.api.searchUsers(username)
      if (response.isSuccessful) {
        Result.Success(response.body()?.items ?: emptyList())
      } else {
        Result.Error("Failed to fetch users")
      }
    } catch (e: Exception) {
      Result.Error("Exception occurred")
    }
  }

  suspend fun fetchUserFollowers(followersUrl: String): Response<List<Any>> {
    return RetrofitInstance.api.getUserFollowersCount(followersUrl)
  }

  suspend fun fetchUserFollowing(followingUrl: String): Response<List<Any>> {
    return RetrofitInstance.api.getUserFollowingCount(followingUrl)
  }
}


