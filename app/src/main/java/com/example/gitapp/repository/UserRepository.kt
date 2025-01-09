package com.example.gitapp.repository


import com.example.gitapp.data.model.UserDataListItem
import com.example.gitapp.di.RetrofitInstance
import com.example.gitapp.utils.Result
import retrofit2.Response

class UserRepository() {

  suspend fun searchUsers(username: String, page: Int, perPage: Int): Result<List<UserDataListItem>> {
    return try {
      val response = RetrofitInstance.api.searchUsers(username, perPage, page)
      if (response.isSuccessful) {
        Result.Success(response.body()?.items ?: emptyList())
      } else {
        Result.Error("Failed to fetch users")
      }
    } catch (e: Exception) {
      Result.Error("Exception occurred: ${e.message}")
    }
  }

  suspend fun fetchUserFollowers(followersUrl: String): Response<List<Any>> {
    return RetrofitInstance.api.getUserFollowersCount(followersUrl)
  }

  suspend fun fetchUserFollowing(followingUrl: String): Response<List<Any>> {
    return RetrofitInstance.api.getUserFollowingCount(followingUrl)
  }
}


