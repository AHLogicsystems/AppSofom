package com.logicsystems.appsofom

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.logicsystems.appsofom.Adapters.GenericBasicAdapter
import com.logicsystems.appsofom.datos.*


class SearchResultClienteActivity : AppCompatActivity() {
    var StrProblema: String = ""
    val service = Service()
    lateinit var listSearchResult: ListView
    private var IntTypeSearch = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result_cliente)
        listSearchResult = findViewById(R.id.listaDeClientes)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Resultados de la búsqueda"

        IntTypeSearch = intent.extras?.getInt("TypeSearch") ?: 0

        val StrNombres: String = intent.extras?.getString("Nombres") ?: ""
        val StrApellidos: String = intent.extras?.getString("Apellidos") ?: ""
        val StrRFC: String = intent.extras?.getString("RFC") ?: ""

        val StrIMEI = AppSofomConfigs.getIMEI(this)
        var StrXML = ""
        val OCapa = ClsCapaNegocios()
        if (!OCapa.getXMLClientes(StrNombres, StrApellidos, StrRFC)){
            var bl = false
            service.alertasError(this).apply {
                setMessage(OCapa.StrProblema)
                setPositiveButton("Aceptar") { dialog, which ->
                    bl = true
                }
            }
            if (bl){
                val intent = Intent(this, MainActivity::class.java)
                this.setResult(RESULT_CANCELED, intent)
                this.finish()
            }
        }
        else{
            if (service.callApi(MetodosApp.MultiWebMethodsApp, arrayOf(AppSofomConfigs.NameEmpresa, ClsCapaNegocios.ClaseNegocios, Metodos.GETCLIENTES, OCapa.StrXMLReturn, UserApp.StrUser, UserApp.StrPass, StrIMEI))){
                service_MultiWebMethodsAppCompleted()
            }
            else{
                service.alertasError(this, service.StrProblema)
            }
        }
    }

    fun service_MultiWebMethodsAppCompleted(){
        val ORespuesta = DeserializeXML<ClienteRepuesta>(service.StrResult)
        try {
            if (ORespuesta.Exitoso){
                if (ORespuesta.Clientes.isNotEmpty()){
                    listSearchResult.adapter = GenericBasicAdapter(this, ORespuesta.Clientes, true)
                }
                else {
                    service.alertasError(this, "No se encontraron registros coincidentes")
                }
            }else{
                var bl = false
                service.alertasError(this).apply {
                    setMessage(ORespuesta.Error)
                    setPositiveButton("Aceptar") { dialog, which ->
                        bl = true
                    }
                }
                if (bl){
                    val intent = Intent(this, ClientesActivity::class.java)
                        .putExtra("TypeSearch", IntTypeSearch)
                    this.setResult(RESULT_CANCELED, intent)
                    this.finish()
                }
            }
        }
        catch (ex: Exception){
            service.alertasError(this, ex.message.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        this.finish()
        return true
    }
}