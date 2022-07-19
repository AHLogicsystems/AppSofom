package com.logicsystems.appsofom

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.datos.ClsConfiguracion
import com.logicsystems.appsofom.datos.Dao.ConfigApp
import com.logicsystems.appsofom.datos.Dao.Configuracion
import com.logicsystems.appsofom.datos.Service
import java.util.concurrent.Executors


open class Config : AppCompatActivity() {
    private val myExecutor = Executors.newSingleThreadExecutor()
    private val myHandler = Handler(Looper.getMainLooper())
    var StrProblema = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

//        var toolbar = findViewById<Toolbar>(R.id.config_toolbar)
//        setActionBar(toolbar)
//        actionBar!!.title = "Configuración"

        val txtEntorno: EditText = findViewById(R.id.txtEntorno)
        val txtViewEmpresa = findViewById<TextView>(R.id.txtViewEmpresa)
        val btnEntorno = findViewById<Button>(R.id.btnEntorno)
        val spinnerEmpresa = findViewById<Spinner>(R.id.spinnerEmpresa)
        val txtUpdateGPS = findViewById<EditText>(R.id.txtUpdateGPS)
        val txtUpdateInfo = findViewById<EditText>(R.id.txtUpdateInfo)
        val btnGuardarConfig = findViewById<Button>(R.id.btnGuardarConfig)
        var txtViewEntornoActual = findViewById<TextView>(R.id.tViewEntornoActual)
        var txtViewEmpresaActual = findViewById<TextView>(R.id.tViewEmpresaActual)
        var txtViewUpdateGPSActual = findViewById<TextView>(R.id.tViewUpdateGPSActual)
        var txtViewUpdateInfoActual = findViewById<TextView>(R.id.tViewUpdateInfoActual)
        val txtViewIdDispositivo = findViewById<TextView>(R.id.tViewIdDispositivo)

        txtViewIdDispositivo.text = "Identificador CIB: " + AppSofomConfigs().getIdInstalacion(this)
        txtViewEmpresa.visibility = View.GONE
        spinnerEmpresa.visibility = View.GONE
        txtUpdateGPS.visibility = View.GONE
        txtUpdateInfo.visibility = View.GONE
        btnGuardarConfig.visibility = View.GONE

        val Obj2 = ClsConfiguracion()
        Obj2.Id = AppSofomConfigs().IdConfiguracion

        btnEntorno.setOnClickListener {
            txtEntorno.text = txtEntorno.text.trim() as Editable?
            if (txtEntorno.text.toString() != ""){
//                val progress = ProgressBar(this)
//                progress.isIndeterminate = true

                txtEntorno.apply {
                    isFocusableInTouchMode = false
                    isFocusable = false
                }
                btnEntorno.isEnabled = false

                val service = Service()
                service.Url = AppSofomConfigs().getURLFUll(txtEntorno.text.toString().uppercase().trim())
                myExecutor.execute {
                    val OJson = service.callApi("AppGetEmpresas", arrayListOf())
                    if(service.StrProblema == ""){
                        val ORespuesta = service.parseJSON<AppListaEmpresa>(OJson)
                        val Mov = arrayListOf<String>()
                        for (OEach in ORespuesta.Empresas){
                            Mov.add(OEach.Empresa)
                        }
                        val AdapterEmpresas = ArrayAdapter(this, R.layout.spinner_item, Mov)
                        myHandler.post {
                            AdapterEmpresas.setDropDownViewResource(R.layout.spinner_dropdown_item)
                            with(spinnerEmpresa)
                            {
                                adapter = AdapterEmpresas
                                setSelection(0, false)
                                prompt = "Selecciona tu ambiente"
                                gravity = android.view.Gravity.CENTER
                                visibility = View.VISIBLE
                            }
                            txtViewEmpresa.visibility = View.VISIBLE
                            spinnerEmpresa.visibility = View.VISIBLE
                            txtUpdateGPS.visibility = View.VISIBLE
                            txtUpdateInfo.visibility = View.VISIBLE
                            btnGuardarConfig.visibility = View.VISIBLE
                        }
                    }
                    else{
                        myHandler.post {
                            Toast.makeText(this, service.StrProblema, Toast.LENGTH_LONG).show()
                            txtEntorno.apply {
                                isFocusableInTouchMode = true
                                isFocusable = true
                            }
                            btnEntorno.isEnabled = true
                        }
                    }
                }
            }
            else
            {
                Toast.makeText(this, "Es necesario introducir un Entorno.",Toast.LENGTH_LONG).show()
            }
        }
        btnGuardarConfig.setOnClickListener {
            var lExecute = false
            val cUpdateGPS: String = txtUpdateGPS.text.toString().trim()
            val cUpdateInfo: String = txtUpdateInfo.text.toString().trim()
            if (cUpdateGPS == "" || cUpdateInfo == "") {
                Toast.makeText(
                    this,
                    "Se debe capturar un valor en los campos Enviar Ubicación y/o Sincronizar Datos.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if ((cUpdateGPS.toIntOrNull() ?: 0) <= 0 || (cUpdateInfo.toIntOrNull() ?: 0) <= 0) {
                    Toast.makeText(
                        this,
                        "El valor capturado en los campos Enviar Ubicación y/o Sincronizar Datos debe ser mayor a 0.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    lExecute = true
                }
            }
            if (lExecute) {
                val ConfigDao = applicationContext as ConfigApp
                val IdConfig = ConfigDao.room.configDao().getIdConfig()
                if (IdConfig != null || IdConfig > 0){
                    val OConfiguracion = Configuracion()
                    OConfiguracion.id = IdConfig
                    OConfiguracion.cEntorno = txtEntorno.toString()
                    OConfiguracion.cEmpresa = spinnerEmpresa.selectedItem.toString().uppercase()
                    OConfiguracion.nMinUpdateGPS = cUpdateGPS.toIntOrNull() ?: 0
                    OConfiguracion.nMinUpdateInfo = cUpdateInfo.toIntOrNull() ?: 0
                    OConfiguracion.cLoginUser = ""
                    OConfiguracion.cLoginPass = ""
                    OConfiguracion.cOperador = ""
                    OConfiguracion.cInfoTicket = ""
                    ConfigDao.room.configDao().update(OConfiguracion)
                }
                else{
                    val OConfiguracion = Configuracion()
                    OConfiguracion.cEntorno = txtEntorno.toString()
                    OConfiguracion.cEmpresa = spinnerEmpresa.selectedItem.toString().uppercase()
                    OConfiguracion.nMinUpdateGPS = cUpdateGPS.toIntOrNull() ?: 0
                    OConfiguracion.nMinUpdateInfo = cUpdateInfo.toIntOrNull() ?: 0
                    OConfiguracion.cLoginUser = ""
                    OConfiguracion.cLoginPass = ""
                    OConfiguracion.cOperador = ""
                    OConfiguracion.cInfoTicket = ""
                    ConfigDao.room.configDao().insert(OConfiguracion)
                }
            }
        }
        AppSofomConfigs().lLoggin = false


    }
}

