package com.limpatprananda.plugin.simpleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
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

                var tempString: String = responseString ?: "null"
                responseString?.let {
                    tempString = ""
                    val repositories = JsonParser().parse(responseString).asJsonArray

                    for (item in repositories){
                        tempString += "full_name : ${item.asJsonObject.get("full_name")}\n"
                    }
                    tempString += "\n\n"
                    repositories[0].asJsonObject.addProperty("full_name", "Dynamically change")
                    tempString += repositories.toString()
                }
                resultText.text = tempString
            }
        })
    }
}
