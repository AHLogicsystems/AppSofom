package com.logicsystems.appsofom

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import com.logicsystems.appsofom.datos.AppSofomConfigs
import com.logicsystems.appsofom.datos.ClsConfiguracion
import com.logicsystems.appsofom.datos.ClsGenerica


open class ConfigActivity : ClsGenerica() {
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
        val txtViewEntornoActual = findViewById<TextView>(R.id.tViewEntornoActual)
        val txtViewEmpresaActual = findViewById<TextView>(R.id.tViewEmpresaActual)
        val txtViewUpdateGPSActual = findViewById<TextView>(R.id.tViewUpdateGPSActual)
        val txtViewUpdateInfoActual = findViewById<TextView>(R.id.tViewUpdateInfoActual)
        val txtViewIdDispositivo = findViewById<TextView>(R.id.tViewIdDispositivo)

        val config = AppSofomConfigs()
        config.LoadConfig(this)
        val Obj2 = ClsConfiguracion()
        Obj2.Id = config.IdConfiguracion

        txtViewIdDispositivo.text = "Identificador CIB: ${config.cIMEI}"
        txtViewEmpresa.visibility = View.GONE
        spinnerEmpresa.visibility = View.GONE
        txtUpdateGPS.visibility = View.GONE
        txtUpdateInfo.visibility = View.GONE
        btnGuardarConfig.visibility = View.GONE

        if (config.IdConfiguracion != 0){
            txtViewEntornoActual.text = config.cNameEntorno
            txtViewEmpresaActual.text = config.cNameEmpresa
            txtViewUpdateGPSActual.text = config.nMinUpdateGPS.toString()
            txtViewUpdateInfoActual.text = config.nMinUpdateInfo.toString()
            txtViewIdDispositivo.text = config.cInfoTicket
        }

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

                service.Url = AppSofomConfigs().getURLFUll(txtEntorno.text.toString().uppercase().trim())
                if (service.AppGetEmpresas()){
                    val OJson = service.cJSON
                    val ORespuesta = service.parseJSON<AppListaEmpresa>(OJson)
                    val Mov = arrayListOf<String>()
                    for (OEach in ORespuesta.Empresas){
                        Mov.add(OEach.Empresa)
                    }
                    val AdapterEmpresas = ArrayAdapter(this, R.layout.spinner_item, Mov)
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
                else{
                    service.alertasError(this, service.StrProblema)
                    txtEntorno.apply {
                        isFocusableInTouchMode = true
                        isFocusable = true
                    }
                    btnEntorno.isEnabled = true
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
                //ConfigSave = new ConfigTask(this);
                try {
                    val Obj = ClsConfiguracion()
                    //ConfigSave.nTipoOperacion = 1;
                    Obj.SetContext(this)
                    Obj.Id = config.IdConfiguracion
                    Obj.cEntorno = txtEntorno.text.toString().uppercase()
                    Obj.cEmpresa = spinnerEmpresa.selectedItem.toString().uppercase()
                    Obj.nMinUpdateGPS = cUpdateGPS.toIntOrNull() ?: 0
                    Obj.nMinUpdateInfo = cUpdateInfo.toIntOrNull() ?: 0
                    Obj.cLoginUser = ""
                    Obj.cLoginPass = ""
                    Obj.cOperador = ""
                    Obj.cInfoTicket = ""
                    Obj.cIMEI = getIMEI()
                    Obj.Guardar()
                }
                catch (ex: Exception){
                    Log.e("Error", ex.message.toString())
                }
                //ConfigSave.Execute(Obj);
                if (this.StrProblema == ""){
                    this.finish()
                }
            }
        }
        AppSofomConfigs().lLoggin = false
    }
    fun getIMEI() : String{
        var cIMEI = ""
        try{
            cIMEI = Settings.Secure.getString(applicationContext.contentResolver,  Settings.Secure.ANDROID_ID)
            cIMEI = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.d("IMEI",cIMEI)
        }
        catch (ex: Exception){
            Log.e("Error en IMEI", ex.message.toString())
        }
        return cIMEI
    }
}

