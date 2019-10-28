package com.limpatprananda.plugin.simpleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception
import java.lang.reflect.Type

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

                val gsonBuilder = GsonBuilder()
                gsonBuilder.setPrettyPrinting()

                val gson = gsonBuilder.create()
                val repositories = gson.fromJson(responseString, Array<Repository>::class.java).toList()

                repositories[1].full_name = "Change dynamicly"
                resultText.text = "onResponse ${repositories[0].full_name} : (to_json) -> ${gson.toJson(repositories)}"
            }
        })
    }
}

data class Repository(
    val id: Int,
    val name: String,
    var full_name: String
)
