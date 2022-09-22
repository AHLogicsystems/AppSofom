package com.logicsystems.appsofom

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.datos.*


open class ConfigActivity : AppCompatActivity() {
    protected var StrProblema: String = ""
    private val service = Service()

    private lateinit var txtEntorno: EditText
    private lateinit var txtViewEmpresa: TextView
    private lateinit var btnEntorno: Button
    private lateinit var spinnerEmpresa: Spinner
    private lateinit var txtUpdateGPS: EditText
    private lateinit var txtUpdateInfo: EditText
    private lateinit var btnGuardarConfig: Button
    private lateinit var txtViewEntornoActual: TextView
    private lateinit var txtViewEmpresaActual: TextView
    private lateinit var txtViewUpdateGPSActual: TextView
    private lateinit var txtViewUpdateInfoActual: TextView
    private lateinit var txtViewIdDispositivo: TextView

    lateinit var progress: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        progress = ProgressDialog.progressDialog(this)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Configuración"

        txtEntorno = findViewById(R.id.txtEntorno)
        txtViewEmpresa = findViewById(R.id.txtViewEmpresa)
        btnEntorno = findViewById(R.id.btnEntorno)
        spinnerEmpresa = findViewById(R.id.spinnerEmpresa)
        txtUpdateGPS = findViewById(R.id.txtUpdateGPS)
        txtUpdateInfo = findViewById(R.id.txtUpdateInfo)
        btnGuardarConfig = findViewById(R.id.btnGuardarConfig)
        txtViewEntornoActual = findViewById(R.id.tViewEntornoActual)
        txtViewEmpresaActual = findViewById(R.id.tViewEmpresaActual)
        txtViewUpdateGPSActual = findViewById(R.id.tViewUpdateGPSActual)
        txtViewUpdateInfoActual = findViewById(R.id.tViewUpdateInfoActual)
        txtViewIdDispositivo = findViewById(R.id.tViewIdDispositivo)

        txtViewIdDispositivo.text = AppSofomConfigs.getIdInstalacion(this)

        txtViewEmpresa.visibility = View.GONE
        spinnerEmpresa.visibility = View.GONE
        txtUpdateGPS.visibility = View.GONE
        txtUpdateInfo.visibility = View.GONE
        btnGuardarConfig.visibility = View.GONE

        if (AppSofomConfigs.IdConfiguracion != 0){
            txtViewEntornoActual.text = AppSofomConfigs.NameEntorno
            txtViewEmpresaActual.text = AppSofomConfigs.NameEmpresa
            txtViewUpdateGPSActual.text = AppSofomConfigs.MinUpdateGPS.toString()
            txtViewUpdateInfoActual.text = AppSofomConfigs.MinUpdateInfo.toString()
            txtViewIdDispositivo.text = AppSofomConfigs.InfoTicket
            txtViewIdDispositivo.text = AppSofomConfigs.getIdInstalacion(this)
        }

        btnEntorno.setOnClickListener {
            progress.show()
            Handler(Looper.getMainLooper()).postDelayed({
                buscarEntorno()
            }, 1000)
        }
        btnGuardarConfig.setOnClickListener {
            progress.show()
            Handler(Looper.getMainLooper()).postDelayed({
                guardarConfiguracion()
            }, 1000)
        }
        AppSofomConfigs.lLoggin = false
    }

    private fun buscarEntorno(){
        if (txtEntorno.text.toString() != ""){

            txtEntorno.apply {
                isFocusableInTouchMode = false
                isFocusable = false
            }
            btnEntorno.isEnabled = false

            service.Url = AppSofomConfigs.getURLFUll(txtEntorno.text.toString().uppercase().trim())
            if (service.callApi(MetodosApp.AppGetEmpresas, emptyArray())){
                service_AppGetEmpresasCompleted()
            }
            else{
                this.StrProblema = service.StrProblema
                txtEntorno.apply {
                    isFocusableInTouchMode = true
                    isFocusable = true
                }
                btnEntorno.isEnabled = true
            }
        }
        else
        {
            this.StrProblema = "Es necesario introducir un Entorno."
        }

        if (this.StrProblema != ""){
            service.alertasError(this, this.StrProblema)
        }
        progress.dismiss()
    }

    private fun service_AppGetEmpresasCompleted() {
        val ORespuesta = DeserializeXML<AppListaEmpresa>(service.StrResult)
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

    private fun guardarConfiguracion(){
        var lExecute = false
        val cUpdateGPS: String = txtUpdateGPS.text.toString().trim()
        val cUpdateInfo: String = txtUpdateInfo.text.toString().trim()
        if (cUpdateGPS == "" || cUpdateInfo == "") {
                this.StrProblema = "Se debe capturar un valor en los campos Enviar Ubicación y/o Sincronizar Datos."
        } else {
            if ((cUpdateGPS.toIntOrNull() ?: 0) <= 0 || (cUpdateInfo.toIntOrNull() ?: 0) <= 0) {
                    this.StrProblema = "El valor capturado en los campos Enviar Ubicación y/o Sincronizar Datos debe ser mayor a 0."
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
                Obj.Id = AppSofomConfigs.IdConfiguracion
                Obj.cEntorno = txtEntorno.text.toString().uppercase()
                Obj.cEmpresa = spinnerEmpresa.selectedItem.toString().uppercase()
                Obj.nMinUpdateGPS = cUpdateGPS.toIntOrNull() ?: 0
                Obj.nMinUpdateInfo = cUpdateInfo.toIntOrNull() ?: 0
                Obj.cLoginUser = ""
                Obj.cLoginPass = ""
                Obj.cOperador = ""
                Obj.cInfoTicket = ""
                Obj.Guardar()

                AppSofomConfigs.NameEntorno = txtEntorno.text.toString()
            }
            catch (ex: Exception){
                Log.e("Error", ex.message.toString())
            }
        }
        if (this.StrProblema != ""){
            service.alertasError(this, this.StrProblema)
            this.StrProblema = ""
        }
        else this.finish()
        progress.dismiss()
    }
}

