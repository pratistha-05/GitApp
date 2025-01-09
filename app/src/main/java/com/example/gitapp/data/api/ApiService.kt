package com.example.gitapp.data.api

import com.example.gitapp.data.model.RepoResponse
import com.example.gitapp.data.model.UserData
import com.example.gitapp.data.model.UserSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

  @GET("search/users")
  suspend fun searchUsers(
    @Query("q") username: String,
    @Query("per_page") perPage: Int,
    @Query("page") page: Int
  ): Response<UserSearchResponse>


  @GET
  suspend fun getUserFollowersCount(@Url followersUrl: String): Response<List<Any>>

  @GET
  suspend fun getUserFollowingCount(@Url followingUrl: String): Response<List<Any>>

  @GET("users/{username}/repos")
  suspend fun getUserRepositories(
    @Path("username") username: String
  ): Response<List<RepoResponse>>
}
