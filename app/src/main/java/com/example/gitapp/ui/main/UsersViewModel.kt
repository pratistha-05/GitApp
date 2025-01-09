package com.example.gitapp.ui.main


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitapp.data.model.UserData
import com.example.gitapp.repository.UserRepository
import com.example.gitapp.utils.Result
import kotlinx.coroutines.launch

class UsersViewModel(private val repository: UserRepository) : ViewModel() {

  val userList: MutableLiveData<Result<List<UserData>>> = MutableLiveData()

  fun searchUsers(username: String) {
    viewModelScope.launch {
      userList.postValue(Result.Loading())

      val result = repository.searchUsers(username)

      when (result) {
        is Result.Success -> {
          val enrichedData = result.data?.let { userDataList ->
            val enrichedList = mutableListOf<UserData>()

            for (userDataItem in userDataList) {
              val followersCount = fetchUserFollowers(userDataItem.followers_url)
              val followingCount = fetchUserFollowing(userDataItem.following_url)

              val enrichedUserData = UserData(
                avatar_url = userDataItem.avatar_url,
                username = userDataItem.login,
                githubURL = userDataItem.html_url,
                followers = followersCount,
                following =followingCount,
                public_repos = userDataItem.score.toInt()
              )

              enrichedList.add(enrichedUserData)
            }

            enrichedList
          }

          userList.postValue(Result.Success(enrichedData ?: emptyList()))
        }
        is Result.Error -> {
          userList.postValue(Result.Error("exception"))
        }
        else -> {
          userList.postValue(Result.Error("Unknown Error"))
        }
      }
    }
  }

  private suspend fun fetchUserFollowers(followersUrl: String): Int {
    return try {
      val response = repository.fetchUserFollowers(followersUrl)
      if (response.isSuccessful) {
        response.body()?.size ?: 0
      } else {
        Log.e("UsersViewModel", "Failed to fetch user followers: ${response}")
      }
    } catch (e: Exception) {
      0
    }
  }

  private suspend fun fetchUserFollowing(followingUrl: String): Int {
    return try {
      val response = repository.fetchUserFollowing(followingUrl)
      if (response.isSuccessful) {
        response.body()?.size ?: 0
      } else {
        0
      }
    } catch (e: Exception) {
      0
    }
  }
}

