package com.limpatprananda.plugin.simpleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                resultText.text = "onResponse ${response.body()}"
            }
        })
    }
}
