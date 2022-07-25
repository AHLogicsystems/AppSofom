package com.logicsystems.appsofom

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.logicsystems.appsofom.datos.ClsConfiguracion
import com.logicsystems.appsofom.datos.Service
import com.logicsystems.appsofom.datos.Utils


open class Login : AppSofom() {
    var StrIMEI: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val config = AppSofomConfigs()
        config.LoadConfig(this)
        if(config.cNameEntorno == "" || config.cNameEmpresa == ""){
            val intent = Intent(this, Config::class.java)
            this.startActivity(intent)
        }
        val txtUserName = findViewById<EditText>(R.id.txtUserName)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val btnLogIn = findViewById<Button>(R.id.btnLogIn)
        val txtViewConfigurar = findViewById<TextView>(R.id.txtViewConfigurar)

        btnLogIn.setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_PHONE_STATE
            )
            if (config.cIMEI == ""){
                this.StrProblema = "Lamentablemente el dispositivo no podra conectarse al servicio de cobro."
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
                        val service = Service()
                        service.Url = config.URLWSFull()
                        Log.d("URL:", service.Url)
                        if (this.StrProblema == ""){
                            myExecutor.execute {
                                val OJson = service.callApi("AppLogin", arrayListOf(txtUserName.toString(), txtPassword.toString(), config.cNameEmpresa, config.cIMEI))
                                if(service.StrProblema == ""){
                                    val ORespuesta = service.parseJSON<AppRespuestaLogIn>(OJson)
                                    if (ORespuesta.Exitoso){
                                        if (config.cLogUser != txtUserName.toString() || config.cLogPass != txtPassword.toString()){
                                            val Data = ClsConfiguracion()
                                            Data.SetContext(this)
                                            if (Data.LoadAll()){
                                                if (Data.c.moveToFirst()){
                                                    Data.Id = Data.c.getString(0).toInt()
                                                    Data.cEntorno = Data.c.getString(1)
                                                    Data.cEmpresa = Data.c.getString(2)
                                                    Data.nMinUpdateGPS = Data.c.getInt(3)
                                                    Data.nMinUpdateInfo = Data.c.getInt(4)
                                                    Data.cLoginUser = txtUserName.toString()
                                                    Data.cLoginPass = txtPassword.toString()
                                                    Data.cOperador = ORespuesta.NickName
                                                    Data.cInfoTicket = ORespuesta.InfoTicket

                                                    if (!Data.Guardar()){
                                                        this.StrProblema = "Error guardando datos de configuración${Data.cProblema}"
                                                    }
                                                    else{
                                                        config.IdConfiguracion = Data.Id
                                                        config.cLogUser = txtUserName.toString()
                                                        config.cLogPass = txtPassword.toString()
                                                        config.cNameOperador = ORespuesta.NickName
                                                        config.cInfoTicket = ORespuesta.InfoTicket
                                                    }
                                                }
                                                else{
                                                    this.StrProblema = "No se ha configurado al menos una vez el sistema."
                                                }
                                            }
                                            else{
                                                this.StrProblema = "Error obteniendo datos de configuración${Data.cProblema}."
                                            }
                                        }
                                    }
                                    else{
                                        this.StrProblema = ORespuesta.Error
                                    }


                                    if (config.cLogUser == "" && config.cLogPass == "" || !ORespuesta.Exitoso) {
                                        this.StrProblema = "Debe al menos haber iniciado session una vez en modo online, para logearse en modo offline."
                                    } else {
                                        if (config.cLogUser == txtUserName.text.toString() && config.cLogPass == txtPassword.text.toString()) {
                                            UserApp.StrUser = txtUserName.text.toString()
                                            UserApp.StrPass = txtPassword.text.toString()
                                            UserApp.StrNickName = config.cNameOperador
                                            txtUserName.setText("")
                                            txtPassword.setText("")
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                        } else {
                                            this.StrProblema = "Nombre de Usuario y Contraseña Incorrecta."
                                        }
                                    }
                                    myHandler.post {
                                        if (this.StrProblema == ""){
                                            UserApp.StrUser = txtUserName.toString()
                                            UserApp.StrPass = txtPassword.toString()
                                            UserApp.StrNickName = ORespuesta.NickName
                                            txtUserName.setText("")
                                            txtPassword.setText("")
                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                            this.finish()
                                        }
                                    }
                                }
                                else{
                                    myHandler.post {
                                        Toast.makeText(this, service.StrProblema, Toast.LENGTH_LONG).show()
                                        txtUserName.isFocusableInTouchMode = true
                                        txtUserName.isFocusable = true
                                        txtPassword.isFocusableInTouchMode = true
                                        txtPassword.isFocusable = true
                                        btnLogIn.isEnabled = true
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                   this.StrProblema = "Es necesario introducir el Usuario y la Contraseña."
                }
            }

            if (this.StrProblema != ""){
                Toast.makeText(
                    this,
                    this.StrProblema,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        txtViewConfigurar.setOnClickListener {
            val intent = Intent(this, Config::class.java)
            this.startActivity(intent)
        }
    }
}