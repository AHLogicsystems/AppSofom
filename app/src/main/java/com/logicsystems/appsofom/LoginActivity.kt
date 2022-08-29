package com.logicsystems.appsofom

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.datos.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


open class LoginActivity : AppCompatActivity() {
    protected var StrProblema: String = ""
    val service = Service()
    lateinit var progress: Dialog
    private lateinit var txtUserName: EditText
    private lateinit var txtPassword: EditText
//    private lateinit var progressBar: ProgressBar
    private val scope = MainScope()


    var StrIMEI = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progress = ProgressDialog.progressDialog(this)

        AppSofomConfigs.LoadConfig(this)
        if (AppSofomConfigs.NameEntorno == "" || AppSofomConfigs.NameEmpresa == ""){
            val intent = Intent(this, ConfigActivity::class.java)
            resultLauncher.launch(intent)
        }
        txtUserName = findViewById(R.id.txtUserName)
        txtPassword = findViewById(R.id.txtPassword)

        btnLogIn.setOnClickListener {
            progress.show()
            Handler(Looper.getMainLooper()).postDelayed({
                LogIn()
                progress.dismiss()
            }, 1000)
        }

        txtViewConfigurar.setOnClickListener {
            Config()
        }
    }
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = Intent(this, LoginActivity::class.java)
            this.startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        scope.cancel() // Destruimos el alcance de la corrutina
    }

    private fun LogIn(){
//        val dialog = service.progressBarCIB(this)
        this.StrIMEI = AppSofomConfigs.getIMEI(this)
        if (this.StrIMEI == ""){
            this.StrProblema = "Lamentablemente el dispositivo no podra conectarse al servicio de cobro."
        }
        else{
            if (txtUserName.text.toString() != "" && txtPassword.text.toString() != ""){
                if (AppSofomConfigs.isOnLine(this)){
                    txtUserName.apply {
                        isFocusable = false
                        isFocusableInTouchMode = false
                    }
                    txtPassword.apply {
                        isFocusable = false
                        isFocusableInTouchMode = false
                    }
                    btnLogIn.isEnabled = false
                    service.Url = AppSofomConfigs.getURLFUll(AppSofomConfigs.NameEntorno)
                    if (service.callApi(MetodosApp.AppLogin, arrayOf(txtUserName.text.toString(), txtPassword.text.toString(), AppSofomConfigs.NameEmpresa, StrIMEI)))
                    {
                        service_AppLoginCompleted()
                    }
                    else{
                        this.StrProblema = service.StrProblema
                    }
                }
                else{
                    if (AppSofomConfigs.LogUser == "" && AppSofomConfigs.LogPass == ""){
                        this.StrProblema = "Debe al menos haber iniciado session una vez en modo online, para logearse en modo offline."
                    }
                    else{
                        if (AppSofomConfigs.LogUser == txtUserName.text.toString() && AppSofomConfigs.LogUser == txtPassword.text.toString()){
                            UserApp.StrUser = txtUserName.text.toString()
                            UserApp.StrPass = txtPassword.text.toString()
                            UserApp.StrNickName = AppSofomConfigs.NameOperador
                            txtUserName.setText("")
                            txtPassword.setText("")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            this.StrProblema = "Nobre de Usuario y Contraseña Incorrecta."
                        }
                    }
                }
            }
        }
        if (this.StrProblema != ""){
            service.alertasError(this, this.StrProblema)
            this.StrProblema = ""
        }
        progress.dismiss()
    }

    private fun Config(){
        val intent = Intent(this, ConfigActivity::class.java)
        this.startActivity(intent)
    }

    private fun service_AppLoginCompleted() {
        val ORespuesta = DeserializeXML<AppRespuestaLogIn>(service.StrResult)
        if (ORespuesta.Exitoso){
            if (AppSofomConfigs.LogUser != txtUserName.text.toString() || AppSofomConfigs.LogPass != txtPassword.text.toString() || AppSofomConfigs.InfoTicket!=ORespuesta.InfoTicket || AppSofomConfigs.NameOperador!=ORespuesta.NickName)
            {
                val Data = ClsConfiguracion()
                Data.SetContext(this)
                if (Data.LoadAll()){
                    if (Data.c.moveToFirst()){
                        Data.Id = Data.c.getString(0).toInt()
                        Data.cEntorno = Data.c.getString(1)
                        Data.cEmpresa = Data.c.getString(2)
                        Data.nMinUpdateGPS = Data.c.getInt(3)
                        Data.nMinUpdateInfo = Data.c.getInt(4)
                        Data.cLoginUser = txtUserName.text.toString()
                        Data.cLoginPass = txtPassword.text.toString()
                        Data.cOperador = ORespuesta.NickName
                        Data.cInfoTicket = ORespuesta.InfoTicket

                        if (!Data.Guardar()){
                            this.StrProblema = "Error guardando datos de configuración: " + Data.cProblema
                        }
                        else{
                            //Actualizamos los valores de configuración.
                            AppSofomConfigs.IdConfiguracion = Data.Id
                            AppSofomConfigs.LogUser = txtUserName.text.toString()
                            AppSofomConfigs.LogPass = txtPassword.text.toString()
                            AppSofomConfigs.NameOperador = ORespuesta.NickName
                            AppSofomConfigs.InfoTicket = ORespuesta.InfoTicket
                        }
                    }
                    else{
                        this.StrProblema = "No se ha configurado al menos una vez el sistema."
                    }
                }
                else{
                    this.StrProblema = "Error obtenido datos de configuración: " + Data.cProblema
                }
            }
            if (this.StrProblema == ""){
                UserApp.StrUser = txtUserName.text.toString()
                UserApp.StrPass = txtPassword.text.toString()
                UserApp.StrNickName = ORespuesta.NickName
                txtUserName.setText("")
                txtPassword.setText("")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }
        else{
            this.StrProblema = ORespuesta.Error
        }
        if (this.StrProblema != ""){
            service.alertasError(this, this.StrProblema)
            this.StrProblema = ""
        }
    }
}