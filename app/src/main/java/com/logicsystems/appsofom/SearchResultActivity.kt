package com.logicsystems.appsofom

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toolbar
import com.logicsystems.appsofom.datos.GenericaActivitys
import com.logicsystems.appsofom.datos.Metodos
import com.logicsystems.appsofom.datos.SolicitudCredito
import com.logicsystems.appsofom.datos.bd.OffLineDisposicion

class SearchResultActivity : GenericaActivitys() {
    lateinit var listSearchResult: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        listSearchResult = findViewById(R.id.listPrestamos)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        toolbar.title = "Resultados de la búsqueda"

        val StrFolio = intent.extras?.getString("Folio") ?: ""
        val StrCliente = intent.extras?.getString("Cliente") ?: ""
        val IntTypeSearch = intent.extras?.getInt("TypeSearch") ?: 0

        service.Url = config.URLWSFull()
        val OCapa = ClsCapaNegocios()
        var bl = false
        when(IntTypeSearch){
            SolicitudCredito.SOLICITUD.ordinal,
            SolicitudCredito.RENOVAR.ordinal,
            SolicitudCredito.ENTREGAR.ordinal,
            SolicitudCredito.COBRAR.ordinal -> {
                if (!OCapa.getJSONSearch(StrFolio, StrCliente, IntTypeSearch)){
                    service.alertasError(this).apply {
                        setMessage(StrProblema)
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
                    service.MultiWebMethodsApp(config.cNameEmpresa, ClaseNegocios, Metodos.APPSEARCH, OCapa.StrJSONResult, UserApp.StrUser, UserApp.StrPass, config.cIMEI)
                }
            }
            SolicitudCredito.REESTRUCTURAR.ordinal -> {
                if (service.isConnected(this)){
                    if (!OCapa.getJSONSearch(StrFolio, StrCliente, IntTypeSearch)){
                        service.alertasError(this).apply {
                            setMessage(StrProblema)
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
                        service.MultiWebMethodsApp(config.cNameEmpresa, ClaseNegocios, Metodos.APPSEARCH, OCapa.StrJSONResult, UserApp.StrUser, UserApp.StrPass, config.cIMEI)
                    }
                }
                else{
                    var ORespuesa = AppSearchRespuesta()
                    if (IntTypeSearch == SolicitudCredito.ENTREGAR.ordinal){
                        var Data = OffLineDisposicion
                        var appCredito = AppCredito()
//                        Data.Set
                        TODO()
                    }
                }
            }
        }

        service.MultiWebMethodsApp(config.cNameEmpresa, ClaseNegocios, Metodos.APPSEARCH, "", UserApp.StrUser, UserApp.StrUser, config.cIMEI)
    }
}