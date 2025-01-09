package com.example.gitapp.data.api

import com.example.gitapp.data.model.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

  @GET("users/{username}/repos")
  suspend fun getUserRepositories(
    @Path("username") username: String
  ): Response<List<UserData>>
}

