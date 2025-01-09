package com.example.gitapp.di

import com.example.gitapp.data.api.ApiService
import com.example.gitapp.utils.AUTH_TOKEN
import com.example.gitapp.utils.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

  private val authInterceptor = Interceptor { chain ->
    val request = chain.request().newBuilder()
      .addHeader("Authorization", "Bearer $AUTH_TOKEN")
      .build()
    chain.proceed(request)
  }

  private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .build()

  val api: ApiService by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(ApiService::class.java)
  }
}
