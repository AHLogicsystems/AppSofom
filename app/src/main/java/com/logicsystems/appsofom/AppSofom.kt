package com.logicsystems.appsofom

import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors

open class AppSofom : AppCompatActivity() {
    val myExecutor = Executors.newSingleThreadExecutor()
    val myHandler = Handler(Looper.getMainLooper())
    var StrProblema = ""

    open fun getIMEI() : String{
        var cIMEI = ""
        try{
            cIMEI = Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
            Log.d("IMEI",cIMEI)
        }
        catch (ex: Exception){
            Log.e("Error en IMEI", ex.message.toString())
        }
        return cIMEI
    }
}