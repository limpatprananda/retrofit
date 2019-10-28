package com.limpatprananda.plugin.simpleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
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

        val call = service.getListRepositories("limpatprananda")
        call.enqueue(object : Callback<List<Repository>> {
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                resultText.text = "onFailure ${t.message}"
            }

            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                val listRepositories = response.body()
                var tempString = listRepositories?.size.toString() ?: "null"
                listRepositories?.let {
                    for (item in listRepositories){
                        tempString += "fullName : ${item.fullName}\n"
                    }
                }
                tempString += "\n\n"
                listRepositories?.get(0)?.fullName = "Dinamically change"
                resultText.text = tempString + listRepositories.toString()
            }
        })
    }
}

data class Repository(
    val id: Int,
    val name: String,
    @SerializedName("full_name")
    var fullName: String
)