package com.logicsystems.appsofom

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.logicsystems.appsofom.datos.CallWebService
import java.util.concurrent.Executors

class activity_soap : AppCompatActivity() {
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soap)

        findViewById<TextView>(R.id.enviarNotificacion).setOnClickListener {
            val StrUser = findViewById<TextInputEditText>(R.id.StrUser).text.toString().trim()
            val StrPass = findViewById<TextInputEditText>(R.id.StrPass).text.toString().trim()
            val StrEmpresa = findViewById<TextInputEditText>(R.id.StrEmpresa).text.toString().trim()
            val StrIMEI = findViewById<TextInputEditText>(R.id.StrIMEI).text.toString().trim()
            when {
                StrUser.isEmpty() || StrPass.isEmpty() || StrEmpresa.isEmpty() || StrIMEI.isEmpty() -> Toast.makeText(this, getString(R.string.drawer_open), Toast.LENGTH_SHORT).show()

                !Utils.isConnected(this@activity_soap) -> Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()

                else -> doMyTask("AppLogin", listOf(StrUser, StrPass, StrEmpresa, StrIMEI))
            }
        }
    }
    fun doMyTask(methodName: String, params: List<String>){
        myExecutor.execute {
            val response = CallWebService.callApi(methodName, params)
            Log.v("response", "response==" + response)
//            myHandler.post {
//                findViewById<TextView>(R.id.resultValue).text = response
//            }
        }
    }
}