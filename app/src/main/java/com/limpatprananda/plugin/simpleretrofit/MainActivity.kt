package com.limpatprananda.plugin.simpleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
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

        val call = service.getListRepositories("limpatprananda")
        call.enqueue(object : Callback<List<Repository>>{
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                resultText.text = "onFailure ${t.message}"
            }

            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                val listRepositories = response.body()
                listRepositories?.get(0)?.fullName = "Dynamicly Change"

                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val type = Types.newParameterizedType(List::class.java, Repository::class.java)
                val jsonAdapter = moshi.adapter<List<Repository>>(type)

                resultText.text = "onResponse ${jsonAdapter.toJson(listRepositories)}"
            }
        })
    }
}

@JsonClass(generateAdapter = true)
data class Repository(
    val id: Int,
    val name: String,
    @Json(name = "full_name")
    var fullName: String
)