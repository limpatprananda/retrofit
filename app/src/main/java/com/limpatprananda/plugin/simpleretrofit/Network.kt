package com.limpatprananda.plugin.simpleretrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

public interface GitHubService{
    @GET("users/{user}/repos")
    fun getListRepositories(@Path("user") user: String) : Call<List<Repository>>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val service = retrofit.create(GitHubService::class.java)