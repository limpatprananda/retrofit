package com.limpatprananda.plugin.simpleretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.NetworkOnMainThreadException
import android.os.StrictMode
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var stringText: String = "Connecting to Internet!!!\n"
    private val DELEYED_SECONDS: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            stringText += "\nclicked.."
            updateText()
        }

        syncButton.setOnClickListener {
            stringText += "\nsync.."
            updateText()
            synchronousRequest()
        }

        asyncButton.setOnClickListener {
            stringText += "\nasync.."
            updateText()
            asynchronousRequest()
        }
        resultText.text = stringText
    }

    fun updateText(){
        resultText.text = stringText
    }

    fun synchronousRequest(){
        val stringCall: Call<String> = service.getPersons(DELEYED_SECONDS)
        val threadPolicy = StrictMode.ThreadPolicy.Builder()
            .permitAll()
            .build()
        StrictMode.setThreadPolicy(threadPolicy)

        val result = stringCall.execute().body()
        stringText += "\n\n" + result
        updateText()
    }

    fun asynchronousRequest(){
        val stringCall: Call<String> = service.getPersons(DELEYED_SECONDS)

        stringCall.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                stringText += "onFailure ${t.message}"
                updateText()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                stringText += "\n\n" + response.body()
                updateText()
            }
        })
    }
}
