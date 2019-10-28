package com.limpatprananda.plugin.simpleretrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

public interface GitHubService{
    @GET("users/{user}/repos")
    fun getListRepositories(@Path("user") user: String) : Call<String>
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .build()

val service = retrofit.create(GitHubService::class.java)