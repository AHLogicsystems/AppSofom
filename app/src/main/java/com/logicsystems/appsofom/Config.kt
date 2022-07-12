package com.logicsystems.appsofom

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.*
import com.logicsystems.appsofom.datos.ClsConfiguracion


open class Config : AppCompatActivity() {
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
        val txtTest = findViewById<TextView>(R.id.txtTest)

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

//                btnEntorno.isEnabled = false

                txtTest.text = "prueba de que si funciona"
                val service = Utils()
                service.Url = AppSofomConfigs().getURLFUll(txtEntorno.text.toString().uppercase().trim())
                service.doMyTask(this, spinnerEmpresa,"AppGetEmpresas", mutableListOf())
                Toast.makeText(this, txtTest.text,Toast.LENGTH_LONG).show()
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
                val Obj = ClsConfiguracion()
                //ConfigSave.nTipoOperacion = 1;
                Obj.Id = AppSofomConfigs().IdConfiguracion
                Obj.cEntorno = txtEntorno.text.toString().uppercase()
                Obj.cEmpresa = spinnerEmpresa.selectedItem.toString().uppercase()
                Obj.nMinUpdateGPS = cUpdateGPS.toIntOrNull() ?: 0
                Obj.nMinUpdateInfo = cUpdateInfo.toIntOrNull() ?: 0
                Obj.cLoginUser = ""
                Obj.cLoginPass = ""
                Obj.cOperador = ""
                Obj.cInfoTicket = ""
                //ConfigSave.Execute(Obj);
            }
        }
        AppSofomConfigs().lLoggin = false
    }
}
