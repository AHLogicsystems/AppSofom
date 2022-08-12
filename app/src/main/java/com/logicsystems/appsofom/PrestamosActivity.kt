package com.logicsystems.appsofom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toolbar
import androidx.activity.result.contract.ActivityResultContracts
import com.logicsystems.appsofom.datos.GenericaActivitys
import com.logicsystems.appsofom.datos.SolicitudCredito
import kotlinx.android.synthetic.main.activity_prestamos.*

class PrestamosActivity : GenericaActivitys() {
    lateinit var txtFolio: EditText
    lateinit var txtCliente: EditText
    private lateinit var IMenuAgregarSol: MenuItem
    var IntTypeSearch = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestamos)

        IntTypeSearch = intent.extras?.getInt("TypeSearch") ?: 0

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = when (IntTypeSearch){
            SolicitudCredito.SOLICITUD.ordinal -> "Solicitud Crédito"
            SolicitudCredito.RENOVAR.ordinal -> "Renovar Crédito"
            SolicitudCredito.REESTRUCTURAR.ordinal -> "Reestructurar Crédito"
            SolicitudCredito.ENTREGAR.ordinal -> "Entregar Crédito"
            SolicitudCredito.COBRAR.ordinal -> "Cobrar Crédito"
            SolicitudCredito.COBROACCESORIO.ordinal -> "Cobro de Accesorios Crédito"
            else ->{
                ""
            }
        }

        btnSearch.setOnClickListener {
            when (IntTypeSearch){
                SolicitudCredito.RENOVAR.ordinal,
                SolicitudCredito.REESTRUCTURAR.ordinal,
                SolicitudCredito.SOLICITUD.ordinal,
                SolicitudCredito.ENTREGAR.ordinal ->
                    if (!service.isConnected(this)){
                        this.StrProblema = "Esta opción solo se encuentra disponible en la modalidad en línea"
                    }
            }
            if (this.StrProblema != ""){
                val intent = Intent(this, SearchResultActivity::class.java)
                intent.putExtra("Folio", txtFolio.text)
                intent.putExtra("Cliente", txtCliente.text)
                intent.putExtra("TypeSearch", IntTypeSearch)
                PrestamoApp().IntIdPrestamo = 0
                resultLauncher.launch(intent)
            }
            else{
                service.alertasError(this, this.StrProblema)
                this.StrProblema = ""
            }
        }
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val IntTypeSearch = result.data?.extras?.getInt("TypeSearch") ?: 0
            when(IntTypeSearch){
                SolicitudCredito.RENOVAR.ordinal ->{
                    val IntentRenovar = Intent(this, RenovarActivity::class.java)
                    this.startActivity(IntentRenovar)
                }
                SolicitudCredito.REESTRUCTURAR.ordinal ->{
                    val IntentReestructurar = Intent(this, ReestructurarActivity::class.java)
                    this.startActivity(IntentReestructurar)
                }
                SolicitudCredito.ENTREGAR.ordinal ->{
                    val IntentEntregar = Intent(this, OperationSearchPrestamo::class.java)
                    this.startActivity(IntentEntregar)
                }
                SolicitudCredito.SOLICITUD.ordinal ->{
                    val IntentSolicitud = Intent(this, SolicitudActivity::class.java)
                    this.startActivity(IntentSolicitud)
                }
                SolicitudCredito.COBROACCESORIO.ordinal ->{
                    val IntentCobroAccesorio = Intent(this, ActivityCobroAccesorios::class.java)
                    this.startActivity(IntentCobroAccesorio)
                }
                else ->{
                    val IntentOperation = Intent(this, OperationActivity::class.java)
                    this.startActivity(IntentOperation)
                }
            }
        }
        else{
            PrestamoApp().IntIdPrestamo = 0
            PrestamoApp().IntIdGrupoSolidario = 0
            PrestamoApp().Integrantes.clear()
        }
    }

    override fun onBackPressed() {
        PrestamoApp().IntIdPrestamo = 0
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cat, menu)
        IMenuAgregarSol = menu.findItem(R.id.menu_add)
        IMenuAgregarSol.title = "Agregar Solicitud"
        IMenuAgregarSol.isVisible = false
        if (IntTypeSearch == SolicitudCredito.ENTREGAR.ordinal) IMenuAgregarSol.isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add -> {
                val intent = Intent(this, SolicitudActivity::class.java)
                PrestamoApp().IntIdPrestamo = 0
                this.startActivity(intent)
            }
            else -> this.finish()
        }
        return true
    }
}