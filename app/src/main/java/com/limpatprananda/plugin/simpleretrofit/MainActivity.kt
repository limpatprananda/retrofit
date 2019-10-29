package com.limpatprananda.plugin.simpleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stringCall: Call<String> = service.getListRepositories("limpatprananda")
        stringCall.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                resultText.text = "onFailure ${t.message}"
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                val responseString = response.body()

                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val type = Types.newParameterizedType(List::class.java, Repository::class.java)
                val jsonAdapter = moshi.adapter<List<Repository>>(type)
                val listRepositories = jsonAdapter.fromJson(responseString)

                listRepositories?.get(0)?.full_name = "Dynamicly Change"

                resultText.text = "onResponse ${jsonAdapter.toJson(listRepositories)}"
            }
        })
    }
}

data class Repository(
    val id: Int,
    val name: String,
    var full_name: String
)