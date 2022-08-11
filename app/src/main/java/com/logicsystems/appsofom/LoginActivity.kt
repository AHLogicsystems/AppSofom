package com.logicsystems.appsofom

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.logicsystems.appsofom.datos.ClsConfiguracion
import com.logicsystems.appsofom.datos.ClsGenerica
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


open class LoginActivity : ClsGenerica() {
    private lateinit var txtUserName: EditText
    private lateinit var txtPassword: EditText
//    private lateinit var progressBar: ProgressBar
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUserName = findViewById(R.id.txtUserName)
        txtPassword = findViewById(R.id.txtPassword)


        btnLogIn.setOnClickListener {
            LogIn()
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        scope.cancel() // Destruimos el alcance de la corrutina
    }

    private fun LogIn(){
        val dialog = service.progressBarCIB(this)
        val user = txtUserName.text.toString()
        val pass = txtPassword.text.toString()
        if (service.isConnected(this)){
            dialog.show()
            config.LoadConfig(this)
            if(config.cNameEntorno != "" && config.cNameEmpresa != ""){
                service.Url = config.URLWSFull()
                service.AppLogin(user, pass, config.cNameEmpresa, config.cIMEI)
                val OJson = service.cJSON
                if (service.StrProblema == ""){
                    val ORespuesta = service.parseJSON<AppRespuestaLogIn>(OJson)
                    if (ORespuesta.Exitoso){
                        try {
                            if (config.cLogUser != user || config.cLogPass != pass) {
                                val Obj = ClsConfiguracion()
                                Obj.SetContext(this)
                                if (Obj.LoadAll()) {
                                    if (Obj.c.moveToFirst()) {
                                        Obj.Id = Obj.c.getString(0).toInt()
                                        Obj.cEntorno = Obj.c.getString(1)
                                        Obj.cEmpresa = Obj.c.getString(2)
                                        Obj.nMinUpdateGPS = Obj.c.getInt(3)
                                        Obj.nMinUpdateInfo = Obj.c.getInt(4)
                                        Obj.cLoginUser = txtUserName.text.toString()
                                        Obj.cLoginPass = txtPassword.text.toString()
                                        Obj.cOperador = ORespuesta.NickName
                                        Obj.cInfoTicket = ORespuesta.InfoTicket
                                        Obj.cIMEI = config.cIMEI

                                        if (!Obj.Guardar()) {
                                            this.StrProblema = "Error guardando datos de configuración ${Obj.cProblema} \n"
                                        } else {
                                            config.IdConfiguracion = Obj.Id
                                            config.cLogUser = txtUserName.text.toString()
                                            config.cLogPass = txtPassword.text.toString()
                                            config.cNameOperador = ORespuesta.NickName
                                            config.cInfoTicket = ORespuesta.InfoTicket

                                            UserApp.StrUser = txtUserName.text.toString()
                                            UserApp.StrPass = txtPassword.text.toString()
                                            UserApp.StrNickName = config.cNameOperador
                                            txtUserName.setText("")
                                            txtPassword.setText("")
                                            val intent = Intent(this, MainActivity::class.java)
                                            this.startActivity(intent)
                                            this.finish()
                                        }
                                    } else {
                                        this.StrProblema += "No se ha configurado al menos una vez el sistema. \n"
                                    }
                                }
                            }
                            else{
                                UserApp.StrUser = txtUserName.text.toString()
                                UserApp.StrPass = txtPassword.text.toString()
                                UserApp.StrNickName = config.cNameOperador
                                txtUserName.setText("")
                                txtPassword.setText("")
                                val intent = Intent(this, MainActivity::class.java)
                                this.startActivity(intent)
                                this.finish()
                            }
                        }
                        catch (ex: Exception){
                            this.StrProblema += "Error obteniendo datos de configuración${ex.message.toString()}. \n"
                        }
                    }
                    else{
                        this.StrProblema += "Usuario y/o Contraseña incorrectos. \n"
                    }
                }
                else{
                    this.StrProblema += service.StrProblema+ "\n"
                }
            }
            else{
                this.StrProblema += "Debe configurar su entorno para conectarse. \n"
            }
        }
        else{
            this.StrProblema += "Debe de estar conectado a internet para utilizar nuestros servicios \n"
        }

        if (this.StrProblema != ""){
            service.alertasError(this, this.StrProblema)
            this.StrProblema = ""
        }
        dialog.dismiss()
    }

    fun ConfigUser(v: View){
        Config()
    }
    private fun Config(){
        val intent = Intent(this, ConfigActivity::class.java)
        this.startActivity(intent)
    }
}