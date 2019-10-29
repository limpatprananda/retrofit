package com.limpatprananda.plugin.simpleretrofit

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

public interface NetworkServices{
    @GET("delayphp/index.php")
    fun getPersons(@Query("seconds") seconds: Int) : Call<String>
}

private val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://www.bjtiport.co.id/tools/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .client(okHttpClient)
    .build()

val service = retrofit.create(NetworkServices::class.java)