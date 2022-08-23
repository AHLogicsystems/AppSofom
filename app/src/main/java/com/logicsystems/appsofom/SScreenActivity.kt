package com.logicsystems.appsofom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.datos.AppSofomConfigs

class SScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppSofomConfigs.LoadConfig(this)
        Handler(Looper.getMainLooper()).postDelayed({

            if(AppSofomConfigs.NameEntorno == "" || AppSofomConfigs.NameEmpresa == ""){
                val intent = Intent(this, ConfigActivity::class.java)
                this.startActivity(intent)
            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            this.finish()
        }, 3000)
    }

}