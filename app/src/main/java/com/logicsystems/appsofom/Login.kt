package com.logicsystems.appsofom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.logicsystems.appsofom.data.ConexionDB
import com.logicsystems.appsofom.datos.Utils
import kotlinx.coroutines.launch

open class Login : AppCompatActivity() {
    var StrIMEI: String = ""

//    val OConfig = applicationContext as ConexionDB
    val OConfig by lazy {applicationContext as ConexionDB}
//    var progress = ProgressBar(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        AppSofomConfigs().LoadConfig(this)

    var Entorno = ""
    var Empresa = ""
    lifecycleScope.launch{
        try {
            val data = OConfig.room.configDao().getConfig()
            Entorno = data.cEntorno
            Empresa = data.cEmpresa
        }
        catch (ex: Exception){
            Log.e("Error", ex.message.toString(), ex.cause)
        }
    }

//        val config = AppSofomConfigs()

        if(Entorno == "" || Empresa == ""){
            val intent = Intent(this, Config::class.java)
            this.startActivity(intent)
        }
        val txtUserName = findViewById<EditText>(R.id.txtUserName)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val btnLogIn = findViewById<Button>(R.id.btnLogIn)
        val txtViewConfigurar = findViewById<TextView>(R.id.txtViewConfigurar)

        btnLogIn.setOnClickListener {
            this.StrIMEI = AppSofomConfigs().getIMEI(this)
            if (this.StrIMEI == ""){
                Toast.makeText(
                    this,
                    "Lamentablemente el dispositivo no podra conectarse al servicio de cobro.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (txtUserName.text.toString() != "" && txtPassword.text.toString() != ""){
                    if (Utils.isConnected(this)){
//                        txtUserName.isFocusableInTouchMode = false
//                        txtUserName.isFocusable = false
//                        txtPassword.isFocusableInTouchMode = false
//                        txtPassword.isFocusable = false
//                        btnLogIn.isEnabled = false
                        val imm: InputMethodManager =
                            btnLogIn.context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        var result = imm.hideSoftInputFromWindow(btnLogIn.windowToken, 0)

//                        progress.isIndeterminate = true
                        val service = Reference()
                        service.Url = AppSofomConfigs().URLWSFull()
//                        service.AppLoginAsync(txtViewConfigurar,txtUserName.text.toString(), txtPassword.text.toString(), AppSofomConfigs().cNameEmpresa, StrIMEI)
                    }
                    if (AppSofomConfigs().cLogUser == "" && AppSofomConfigs().cLogPass == "") {
                        Toast.makeText(
                            this,
                            "Debe al menos haber iniciado session una vez en modo online, para logearse en modo offline.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        if (AppSofomConfigs().cLogUser == txtUserName.text.toString() && AppSofomConfigs().cLogPass == txtPassword.text.toString()) {
                            UserApp.StrUser = txtUserName.text.toString()
                            UserApp.StrPass = txtPassword.text.toString()
                            UserApp.StrNickName = AppSofomConfigs().cNameOperador
                            txtUserName.setText("")
                            txtPassword.setText("")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "Nobre de Usuario y Contraseña Incorrecta.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                else
                {
                    Toast.makeText(
                        this,
                        "Es necesario introducir el Usuario y la Contraseña.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        txtViewConfigurar.setOnClickListener {
            val intent = Intent(this, Config::class.java)
            this.startActivity(intent)
        }
    }
}