package com.limpatprananda.plugin.simpleretrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

public interface GitHubService{
    @GET("users/{user}/repos")
    fun getListRepositories(@Path("user") user: String) : Call<List<Repository>>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

val service = retrofit.create(GitHubService::class.java)